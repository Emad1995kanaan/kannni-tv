package com.kanaani.ibo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanaani.ibo.Brand
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray

/** Élément favori, avec ce qu'il faut pour l'afficher et le lancer sans recharger la catégorie. */
data class Favorite(val kind: String, val id: String, val name: String, val image: String?, val ext: String = "")

/**
 * Réglages et état persistants (SharedPreferences) : identifiants, langue, favoris. Les valeurs lues par l'interface
 * sont des états Compose : un changement (langue, connexion) recompose tout de suite.
 */
object Store {
    private lateinit var sp: SharedPreferences

    var language by mutableStateOf("ar")
        private set
    var session by mutableStateOf<Xtream?>(null)
        private set
    var favorites by mutableStateOf<List<Favorite>>(emptyList())
        private set
    /** Ordre des catégories choisi par l'utilisateur, par type ("live", "movie", "series") : liste d'identifiants. */
    var categoryOrders by mutableStateOf<Map<String, List<String>>>(emptyMap())
        private set
    /** Noms de catégories choisis par l'utilisateur, par type puis par identifiant. */
    var categoryNames by mutableStateOf<Map<String, Map<String, String>>>(emptyMap())
        private set

    fun init(ctx: Context) {
        sp = ctx.getSharedPreferences("kanaani", Context.MODE_PRIVATE)
        language = sp.getString("lang", "ar") ?: "ar"
        val u = sp.getString("user", null)
        val p = sp.getString("pass", null)
        session = if (!u.isNullOrEmpty() && p != null && Brand.host.isNotEmpty()) Xtream(Brand.host, u, p) else null
        favorites = decode(sp.getString("favorites", null))
        categoryOrders = listOf("live", "movie", "series").associateWith { k ->
            sp.getString("order_$k", null)?.split('\n')?.filter { it.isNotEmpty() }.orEmpty()
        }
        categoryNames = listOf("live", "movie", "series").associateWith { k -> decodeNames(sp.getString("names_$k", null)) }
    }

    /** Renomme une catégorie ; nom vide = nom d'origine. */
    fun renameCategory(kind: String, id: String, name: String) {
        val current = categoryNames[kind].orEmpty()
        val updated = if (name.isBlank()) current - id else current + (id to name.trim())
        categoryNames = categoryNames + (kind to updated)
        sp.edit().putString("names_$kind", JsonObject(updated.mapValues { JsonPrimitive(it.value) }).toString()).apply()
    }

    /** Catégories dans l'ordre et sous les noms choisis par l'utilisateur. */
    fun arrange(kind: String, list: List<Category>): List<Category> {
        val names = categoryNames[kind].orEmpty()
        return ordered(kind, list) { it.id }.map { c -> names[c.id]?.let { c.copy(name = it) } ?: c }
    }

    private fun decodeNames(raw: String?): Map<String, String> = runCatching {
        if (raw.isNullOrBlank()) return emptyMap()
        (Json.parseToJsonElement(raw) as JsonObject).mapValues { (it.value as JsonPrimitive).contentOrNull.orEmpty() }.filterValues { it.isNotEmpty() }
    }.getOrDefault(emptyMap())

    fun setCategoryOrder(kind: String, ids: List<String>) {
        categoryOrders = categoryOrders + (kind to ids)
        if (ids.isEmpty()) sp.edit().remove("order_$kind").apply()
        else sp.edit().putString("order_$kind", ids.joinToString("\n")).apply()
    }

    /** Applique l'ordre choisi : catégories rangées d'abord, nouvelles catégories du serveur ensuite (ordre d'origine). */
    fun <T> ordered(kind: String, list: List<T>, id: (T) -> String): List<T> {
        val order = categoryOrders[kind].orEmpty()
        if (order.isEmpty()) return list
        val rank = order.withIndex().associate { (i, v) -> v to i }
        return list.withIndex().sortedBy { rank[id(it.value)] ?: (order.size + it.index) }.map { it.value }
    }

    fun chooseLanguage(code: String) {
        language = code
        sp.edit().putString("lang", code).apply()
    }

    fun login(x: Xtream) {
        sp.edit().putString("user", x.username).putString("pass", x.password).apply()
        session = x
    }

    fun logout() {
        sp.edit().remove("user").remove("pass").remove("favorites").apply()
        // L'ordre des catégories est une préférence de l'appareil : il reste après une déconnexion.
        favorites = emptyList()
        session = null
    }

    fun isFavorite(kind: String, id: String) = favorites.any { it.kind == kind && it.id == id }

    fun toggleFavorite(f: Favorite) {
        favorites = if (isFavorite(f.kind, f.id)) favorites.filterNot { it.kind == f.kind && it.id == f.id } else favorites + f
        sp.edit().putString("favorites", encode(favorites)).apply()
    }

    private fun encode(list: List<Favorite>): String = JsonArray(list.map { f ->
        JsonObject(mapOf(
            "k" to JsonPrimitive(f.kind), "i" to JsonPrimitive(f.id), "n" to JsonPrimitive(f.name),
            "m" to JsonPrimitive(f.image ?: ""), "e" to JsonPrimitive(f.ext),
        ))
    }).toString()

    private fun decode(raw: String?): List<Favorite> = runCatching {
        if (raw.isNullOrBlank()) return emptyList()
        Json.parseToJsonElement(raw).jsonArray.mapNotNull { e ->
            val o = e as? JsonObject ?: return@mapNotNull null
            fun s(k: String) = (o[k] as? JsonPrimitive)?.contentOrNull.orEmpty()
            Favorite(s("k"), s("i"), s("n"), s("m").ifEmpty { null }, s("e"))
        }
    }.getOrDefault(emptyList())
}
