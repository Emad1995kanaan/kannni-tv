package com.kanaani.ibo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/** Client HTTP partagé (API, lecteur, images). */
object Http {
    const val USER_AGENT = "KanaaniIBO/1.0 (Android)"
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .followRedirects(true)
        .addInterceptor { chain -> chain.proceed(chain.request().newBuilder().header("User-Agent", USER_AGENT).build()) }
        .build()
}

data class Account(
    val status: String?,
    val expiresAt: Long?,
    val activeConnections: Int?,
    val maxConnections: Int?,
    val createdAt: Long?,
    val trial: Boolean,
)

data class Category(val id: String, val name: String)

data class Channel(val id: String, val name: String, val logo: String?, val number: Int, val categoryId: String?)

data class Movie(val id: String, val name: String, val poster: String?, val rating: String?, val ext: String, val categoryId: String?)

data class Show(val id: String, val name: String, val poster: String?, val rating: String?, val categoryId: String?)

data class MovieInfo(
    val plot: String?, val genre: String?, val year: String?, val duration: String?,
    val backdrop: String?, val director: String?, val cast: String?, val rating: String?,
)

data class Episode(val id: String, val title: String, val number: Int, val season: Int, val ext: String, val plot: String?, val image: String?, val duration: String?)

data class ShowInfo(
    val plot: String?, val genre: String?, val year: String?, val backdrop: String?, val cast: String?, val rating: String?,
    val seasons: Map<Int, List<Episode>>,
)

private val json = Json { ignoreUnknownKeys = true; isLenient = true }

private fun JsonObject.str(k: String): String? =
    (this[k] as? JsonPrimitive)?.contentOrNull?.trim()?.takeIf { it.isNotEmpty() && it != "null" }

private fun JsonObject.int(k: String): Int? = str(k)?.toDoubleOrNull()?.toInt()

private fun JsonElement?.objects(): List<JsonObject> = when (this) {
    is JsonArray -> mapNotNull { it as? JsonObject }
    is JsonObject -> values.mapNotNull { it as? JsonObject } // certains serveurs renvoient un objet indexé
    else -> emptyList()
}

private fun enc(s: String) = URLEncoder.encode(s, "UTF-8")

/**
 * API Xtream Codes, chargée À LA DEMANDE (comme IBO Player) : rien n'est téléchargé d'avance ; chaque catégorie est
 * lue au moment où on l'ouvre puis gardée en mémoire pour la session.
 */
class Xtream(val host: String, val username: String, val password: String) {
    private val cache = ConcurrentHashMap<String, Any>()

    private fun url(action: String?, extra: String = ""): String {
        val base = "$host/player_api.php?username=${enc(username)}&password=${enc(password)}"
        return if (action == null) base else "$base&action=$action$extra"
    }

    private suspend fun get(url: String): JsonElement? = withContext(Dispatchers.IO) {
        runCatching {
            Http.client.newCall(Request.Builder().url(url).build()).execute().use { r ->
                if (!r.isSuccessful) null else json.parseToJsonElement(r.body.string())
            }
        }.getOrNull()
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T : Any> cached(key: String, load: suspend () -> T?): T? {
        (cache[key] as? T)?.let { return it }
        val v = load() ?: return null
        cache[key] = v
        return v
    }

    fun clearCache() = cache.clear()

    /** null = serveur injoignable ou réponse illisible ; status null = identifiants refusés. */
    suspend fun account(): Account? {
        val root = get(url(null)) as? JsonObject ?: return null
        val ui = root["user_info"] as? JsonObject ?: return Account(null, null, null, null, null, false)
        if (ui.str("auth") == "0") return Account(null, null, null, null, null, false)
        return Account(
            status = ui.str("status"),
            expiresAt = ui.str("exp_date")?.toLongOrNull()?.takeIf { it > 0 }?.times(1000),
            activeConnections = ui.int("active_cons"),
            maxConnections = ui.int("max_connections"),
            createdAt = ui.str("created_at")?.toLongOrNull()?.takeIf { it > 0 }?.times(1000),
            trial = ui.str("is_trial") == "1",
        )
    }

    private suspend fun categories(action: String): List<Category> = cached(action) {
        get(url(action))?.objects()?.mapNotNull { o ->
            val id = o.str("category_id") ?: return@mapNotNull null
            Category(id, o.str("category_name") ?: id)
        }
    } ?: emptyList()

    suspend fun liveCategories() = categories("get_live_categories")
    suspend fun movieCategories() = categories("get_vod_categories")
    suspend fun showCategories() = categories("get_series_categories")

    /** categoryId null = toutes les chaînes. */
    suspend fun channels(categoryId: String?): List<Channel> = cached("live:${categoryId ?: "*"}") {
        val extra = categoryId?.let { "&category_id=${enc(it)}" } ?: ""
        get(url("get_live_streams", extra))?.objects()?.mapNotNull { o ->
            val id = o.str("stream_id") ?: return@mapNotNull null
            Channel(id, o.str("name") ?: id, o.str("stream_icon"), o.int("num") ?: 0, o.str("category_id"))
        }
    } ?: emptyList()

    /** categoryId null = tous les films. */
    suspend fun movies(categoryId: String?): List<Movie> = cached("vod:${categoryId ?: "*"}") {
        get(url("get_vod_streams", categoryId?.let { "&category_id=${enc(it)}" } ?: ""))?.objects()?.mapNotNull { o ->
            val id = o.str("stream_id") ?: return@mapNotNull null
            Movie(id, o.str("name") ?: id, o.str("stream_icon") ?: o.str("cover"), o.str("rating"), o.str("container_extension") ?: "mp4", o.str("category_id"))
        }
    } ?: emptyList()

    /** categoryId null = toutes les séries. */
    suspend fun shows(categoryId: String?): List<Show> = cached("series:${categoryId ?: "*"}") {
        get(url("get_series", categoryId?.let { "&category_id=${enc(it)}" } ?: ""))?.objects()?.mapNotNull { o ->
            val id = o.str("series_id") ?: return@mapNotNull null
            Show(id, o.str("name") ?: id, o.str("cover"), o.str("rating"), o.str("category_id"))
        }
    } ?: emptyList()

    suspend fun movieInfo(id: String): MovieInfo? = cached("vodinfo:$id") {
        val root = get(url("get_vod_info", "&vod_id=${enc(id)}")) as? JsonObject ?: return@cached null
        val i = root["info"] as? JsonObject ?: JsonObject(emptyMap())
        val backdrop = (i["backdrop_path"] as? JsonArray)?.firstOrNull()?.let { (it as? JsonPrimitive)?.contentOrNull }
            ?: i.str("backdrop_path") ?: i.str("cover_big") ?: i.str("movie_image")
        MovieInfo(
            plot = i.str("plot") ?: i.str("description"),
            genre = i.str("genre"),
            year = (i.str("releasedate") ?: i.str("release_date"))?.take(4),
            duration = i.str("duration"),
            backdrop = backdrop,
            director = i.str("director"),
            cast = i.str("cast") ?: i.str("actors"),
            rating = i.str("rating"),
        )
    }

    suspend fun showInfo(id: String): ShowInfo? = cached("seriesinfo:$id") {
        val root = get(url("get_series_info", "&series_id=${enc(id)}")) as? JsonObject ?: return@cached null
        val i = root["info"] as? JsonObject ?: JsonObject(emptyMap())
        val episodes = root["episodes"]
        val seasons = sortedMapOf<Int, List<Episode>>()
        val groups: List<Pair<Int, List<JsonObject>>> = when (episodes) {
            is JsonObject -> episodes.entries.map { (k, v) -> (k.toIntOrNull() ?: 0) to v.objects() }
            is JsonArray -> episodes.mapIndexed { idx, v -> (idx + 1) to v.objects() }
            else -> emptyList()
        }
        for ((season, list) in groups) {
            val eps = list.mapNotNull { o ->
                val eid = o.str("id") ?: return@mapNotNull null
                val info = o["info"] as? JsonObject
                Episode(
                    id = eid,
                    title = o.str("title") ?: eid,
                    number = o.int("episode_num") ?: 0,
                    season = o.int("season") ?: season,
                    ext = o.str("container_extension") ?: "mp4",
                    plot = info?.str("plot"),
                    image = info?.str("movie_image"),
                    duration = info?.str("duration"),
                )
            }.sortedBy { it.number }
            if (eps.isNotEmpty()) seasons[season] = eps
        }
        val backdrop = (i["backdrop_path"] as? JsonArray)?.firstOrNull()?.let { (it as? JsonPrimitive)?.contentOrNull } ?: i.str("cover")
        ShowInfo(
            plot = i.str("plot"), genre = i.str("genre"), year = (i.str("releaseDate") ?: i.str("release_date"))?.take(4),
            backdrop = backdrop, cast = i.str("cast"), rating = i.str("rating"), seasons = seasons,
        )
    }

    fun liveUrl(id: String, hls: Boolean = false) = "$host/live/${enc(username)}/${enc(password)}/$id.${if (hls) "m3u8" else "ts"}"
    fun movieUrl(m: Movie) = "$host/movie/${enc(username)}/${enc(password)}/${m.id}.${m.ext}"
    fun episodeUrl(e: Episode) = "$host/series/${enc(username)}/${enc(password)}/${e.id}.${e.ext}"
}
