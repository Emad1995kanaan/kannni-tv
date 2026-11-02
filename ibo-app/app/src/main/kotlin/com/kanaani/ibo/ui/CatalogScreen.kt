package com.kanaani.ibo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kanaani.ibo.data.Category
import com.kanaani.ibo.data.Movie
import com.kanaani.ibo.data.Show
import com.kanaani.ibo.data.Xtream

/** Affiche commune aux films et aux séries. */
data class Poster(val id: String, val name: String, val image: String?, val rating: String?, val movie: Movie? = null, val show: Show? = null)

@Composable
fun CatalogScreen(nav: Navigator, x: Xtream, s: Screen.Catalog) {
    val serverCategories by produceState<List<Category>?>(null, x, s.series) {
        value = if (s.series) x.showCategories() else x.movieCategories()
    }
    // Ordre choisi dans Réglages › Ordre des films / séries.
    val categories = remember(serverCategories, com.kanaani.ibo.data.Store.categoryOrders, com.kanaani.ibo.data.Store.categoryNames) {
        serverCategories?.let { com.kanaani.ibo.data.Store.arrange(if (s.series) "series" else "movie", it) }
    }
    LaunchedEffect(categories) {
        if (s.category == null) s.category = categories?.firstOrNull()?.id
    }
    var reload by remember { mutableIntStateOf(0) }
    val items by produceState<List<Poster>?>(null, s.category, reload) {
        value = null
        val c = s.category ?: run { value = emptyList(); return@produceState }
        val cat = if (c == Screen.ALL) null else c
        value = if (s.series) x.shows(cat).map { Poster(it.id, it.name, it.poster, it.rating, show = it) }
        else x.movies(cat).map { Poster(it.id, it.name, it.poster, it.rating, movie = it) }
    }
    val shown = remember(items, s.filter) {
        val q = s.filter.trim()
        items?.let { list -> if (q.isEmpty()) list else list.filter { it.name.contains(q, ignoreCase = true) } }
    }

    val catFocus = remember { FocusRequester() }
    val gridFocus = remember { FocusRequester() }
    // Retour depuis une fiche : le focus revient sur l'affiche ouverte ; sinon sur la première catégorie.
    LaunchedEffect(items) {
        val list = shown ?: return@LaunchedEffect
        if (s.focusIndex >= 0 && list.isNotEmpty()) {
            runCatching { s.gridState.scrollToItem(s.focusIndex.coerceIn(0, list.lastIndex)) }
            InitialFocusOnce(gridFocus)
        } else InitialFocusOnce(catFocus)
    }

    Backdrop {
        Column(Modifier.fillMaxSize().padding(horizontal = 20.dp, vertical = 14.dp)) {
            Header(if (s.series) Str.series else Str.movies)
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(Modifier.width(210.dp).fillMaxHeight().clip(RoundedCornerShape(14.dp)).background(C.panel).padding(8.dp)) {
                    val cats = categories
                    when {
                        cats == null -> Loading()
                        cats.isEmpty() -> Message(Str.empty)
                        else -> LazyColumn(state = s.catState, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            // « Tous » en tête de liste.
                            itemsIndexed(listOf(Category(Screen.ALL, Str.all)) + cats, key = { _, c -> c.id }) { i, c ->
                                val selected = s.category == c.id
                                val m = if (selected || (i == 0 && s.category == null)) Modifier.focusRequester(catFocus) else Modifier
                                Tile({ if (s.category != c.id) { s.category = c.id; s.focusIndex = -1; s.filter = "" } },
                                    m.fillMaxWidth().height(38.dp), RoundedCornerShape(10.dp),
                                    colors = listOf(if (selected) C.cardHi else Color.Transparent), focusColors = C.live, focusScale = 1.02f) {
                                    Box(Modifier.fillMaxSize().padding(horizontal = 12.dp), contentAlignment = Alignment.CenterStart) {
                                        Label(c.name, 13.sp, if (selected) C.text else C.text2, if (selected) FontWeight.Bold else FontWeight.Medium)
                                    }
                                }
                            }
                        }
                    }
                }
                Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Field(s.filter, { s.filter = it }, Str.search, Modifier.width(300.dp), imeAction = androidx.compose.ui.text.input.ImeAction.Search)
                    val list = shown
                    when {
                        list == null -> Loading()
                        list.isEmpty() -> Message(if (items == null) Str.loading else Str.empty)
                        else -> LazyVerticalGrid(
                            columns = GridCells.Adaptive(118.dp),
                            state = s.gridState,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(top = 4.dp, bottom = 16.dp, start = 4.dp, end = 4.dp),
                        ) {
                            itemsIndexed(list, key = { _, p -> p.id }) { i, p ->
                                val target = if (s.focusIndex >= 0) s.focusIndex.coerceIn(0, list.lastIndex) else 0
                                val m = if (i == target) Modifier.focusRequester(gridFocus) else Modifier
                                PosterCard(p, m.onFocusChanged { if (it.isFocused) s.focusIndex = i }) {
                                    s.focusIndex = i
                                    if (p.movie != null) nav.go(Screen.MovieDetail(p.movie)) else if (p.show != null) nav.go(Screen.ShowDetail(p.show))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PosterCard(p: Poster, modifier: Modifier, onClick: () -> Unit) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Tile(onClick, Modifier.fillMaxWidth().aspectRatio(2f / 3f), RoundedCornerShape(10.dp), colors = listOf(C.card), focusScale = 1.06f) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Label(p.name, 12.sp, C.text3, lines = 3, modifier = Modifier.padding(8.dp))
            }
            AsyncImage(p.image, p.name, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            val r = p.rating?.toDoubleOrNull()?.takeIf { it > 0 }
            if (r != null) Box(Modifier.align(Alignment.TopStart).padding(6.dp).clip(RoundedCornerShape(6.dp))
                .background(Brush.linearGradient(C.live)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                Label("★ " + String.format(java.util.Locale.ROOT, "%.1f", r), 10.sp, weight = FontWeight.Bold)
            }
        }
        Label(p.name, 12.sp, C.text, FontWeight.SemiBold, lines = 2)
    }
}
