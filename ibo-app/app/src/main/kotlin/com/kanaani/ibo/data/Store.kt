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

    fun init(ctx: Context) {
        sp = ctx.getSharedPreferences("kanaani", Context.MODE_PRIVATE)
        language = sp.getString("lang", "ar") ?: "ar"
        val u = sp.getString("user", null)
        val p = sp.getString("pass", null)
        session = if (!u.isNullOrEmpty() && p != null && Brand.host.isNotEmpty()) Xtream(Brand.host, u, p) else null
        favorites = decode(sp.getString("favorites", null))
    }

    fun setLanguage(code: String) {
        language = code
        sp.edit().putString("lang", code).apply()
    }

    fun login(x: Xtream) {
        sp.edit().putString("user", x.username).putString("pass", x.password).apply()
        session = x
    }

    fun logout() {
        sp.edit().remove("user").remove("pass").remove("favorites").apply()
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
