package com.kanaani.ibo.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kanaani.ibo.data.Channel
import com.kanaani.ibo.data.Episode
import com.kanaani.ibo.data.Movie
import com.kanaani.ibo.data.Show

/**
 * Écrans de l'application. Chaque écran garde son état (catégorie choisie, défilement, élément focalisé) dans son
 * objet : on le retrouve tel quel en revenant en arrière.
 */
sealed class Screen {
    object Home : Screen()
    class Live : Screen() {
        var category by mutableStateOf<String?>(FAVORITES)
        var focusIndex by mutableIntStateOf(0)
        val catState = LazyListState()
        val listState = LazyListState()
        var filter by mutableStateOf("")
        /** Chaîne affichée dans l'aperçu (un 2e appui ouvre le plein écran). */
        var playing by mutableStateOf<Channel?>(null)
    }
    class Catalog(val series: Boolean) : Screen() {
        var category by mutableStateOf<String?>(null)
        var focusIndex by mutableIntStateOf(-1)
        val catState = LazyListState()
        val gridState = LazyGridState()
        var filter by mutableStateOf("")
    }
    class MovieDetail(val movie: Movie) : Screen()
    class ShowDetail(val show: Show) : Screen() {
        var season by mutableStateOf<Int?>(null)
    }
    /** Lecture plein écran. [channels]/[index] : liste pour zapper (Direct) ; [episodes] : épisodes suivants. */
    class Play(
        val title: String,
        val url: String,
        val live: Boolean,
        val channels: List<Channel> = emptyList(),
        val index: Int = 0,
        val episodes: List<Episode> = emptyList(),
    ) : Screen()
    object Favorites : Screen()
    /** Ordre des catégories d'un type : "live", "movie" ou "series". */
    class Reorder(val kind: String) : Screen()
    object Account : Screen()
    object Settings : Screen()

    companion object {
        /** Catégorie spéciale du Direct : les favoris. */
        const val FAVORITES = "__fav__"
        /** Catégorie spéciale du Direct : toutes les chaînes. */
        const val ALL = "__all__"
    }
}

/** Pile de navigation : Retour dépile ; la racine est l'accueil. */
class Navigator {
    val stack = mutableStateListOf<Screen>(Screen.Home)
    val top: Screen get() = stack.last()
    fun go(s: Screen) { stack.add(s) }
    fun replaceTop(s: Screen) { stack[stack.lastIndex] = s }
    /** false si on est déjà à la racine (l'activité se ferme). */
    fun back(): Boolean = if (stack.size > 1) { stack.removeAt(stack.lastIndex); true } else false
    fun reset() { stack.clear(); stack.add(Screen.Home) }
}
