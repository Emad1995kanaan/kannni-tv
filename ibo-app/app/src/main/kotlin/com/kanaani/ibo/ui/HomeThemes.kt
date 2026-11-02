package com.kanaani.ibo.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kanaani.ibo.Brand
import com.kanaani.ibo.BuildConfig
import com.kanaani.ibo.data.Ads
import com.kanaani.ibo.data.Store
import com.kanaani.ibo.data.Xtream
import kotlinx.coroutines.delay

// ───────────── Thèmes d'accueil : 0 = classique, 1 = panneau publicitaire, 2 = menu latéral ─────────────

/** Diaporama publicitaire : change d'image toutes les [Ads.interval] secondes (fondu), points de position en bas. */
@Composable
fun AdCarousel(images: List<String>, modifier: Modifier, corner: Dp = 18.dp) {
    var index by remember(images) { mutableIntStateOf(0) }
    LaunchedEffect(images, Ads.interval) {
        if (images.size < 2) return@LaunchedEffect
        while (true) { delay(Ads.interval * 1000L); index = (index + 1) % images.size }
    }
    Box(modifier.clip(RoundedCornerShape(corner)).background(Brush.linearGradient(listOf(C.cardHi, C.panel)))) {
        if (images.isEmpty()) {
            // Pas encore de publicité : logo de la marque.
            Row(Modifier.align(Alignment.Center), verticalAlignment = Alignment.CenterVertically) {
                Logo(72.dp)
                Spacer(Modifier.width(14.dp))
                Text(Brand.name, color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }
        } else {
            Crossfade(images[index.coerceIn(0, images.lastIndex)], animationSpec = tween(700), label = "ad") { url ->
                AsyncImage(url, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
            if (images.size > 1) Row(Modifier.align(Alignment.BottomCenter).padding(bottom = 10.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                images.indices.forEach { i ->
                    Box(Modifier.height(6.dp).width(if (i == index) 20.dp else 6.dp).clip(RoundedCornerShape(3.dp))
                        .background(if (i == index) Color.White else Color(0x88FFFFFF)))
                }
            }
        }
    }
}

@Composable
private fun ThemeHeader(now: Long, logo: Dp = 54.dp) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Logo(logo)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(Brand.name, color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 26.sp, maxLines = 1)
            Label(Str.welcome, 14.sp, C.text2, FontWeight.SemiBold)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(clock(now), color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 24.sp, maxLines = 1)
            Label(date(now, "EEEE d MMMM"), 11.sp, C.text2)
        }
    }
}

@Composable
private fun StackTile(label: String, icon: String, colors: List<Color>, modifier: Modifier, onClick: () -> Unit) {
    Tile(onClick, modifier.fillMaxWidth(), RoundedCornerShape(16.dp), colors = colors, focusScale = 1.04f) {
        Row(Modifier.fillMaxSize().padding(horizontal = 18.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, 40.dp, Color.White, stroke = 1.5f)
            Spacer(Modifier.width(14.dp))
            Text(label, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 21.sp, maxLines = 1)
        }
    }
}

@Composable
private fun BottomTile(label: String, icon: String, modifier: Modifier, onClick: () -> Unit) {
    Tile(onClick, modifier.fillMaxHeight(), RoundedCornerShape(12.dp), colors = C.small, focusColors = C.live) {
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Icon(icon, 22.dp, Color.White, stroke = 1.8f)
            Spacer(Modifier.width(10.dp))
            Label(label, 15.sp, weight = FontWeight.SemiBold)
        }
    }
}

/** Thème « panneau publicitaire » : Direct / Films / Séries empilés à droite, publicité 16:9 à côté, raccourcis en bas. */
@Composable
fun BillboardHome(nav: Navigator, now: Long) {
    val first = remember { FocusRequester() }
    InitialFocus(first)
    Backdrop {
        Column(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 14.dp)) {
            ThemeHeader(now)
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(Modifier.width(250.dp).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    StackTile(Str.live, Icons.Live, C.live, Modifier.weight(1f).focusRequester(first)) { nav.go(Screen.Live()) }
                    StackTile(Str.movies, Icons.Movies, C.movies, Modifier.weight(1f)) { nav.go(Screen.Catalog(series = false)) }
                    StackTile(Str.series, Icons.Series, C.series, Modifier.weight(1f)) { nav.go(Screen.Catalog(series = true)) }
                }
                AdCarousel(Ads.billboard, Modifier.weight(1f).fillMaxHeight())
            }
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth().height(46.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BottomTile(Str.favorites, Icons.Heart, Modifier.weight(1f)) { nav.go(Screen.Favorites) }
                BottomTile(Str.account, Icons.Account, Modifier.weight(1f)) { nav.go(Screen.Account) }
                BottomTile(Str.settings, Icons.Settings, Modifier.weight(1f)) { nav.go(Screen.Settings) }
            }
            Spacer(Modifier.height(6.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Label(expiryLine(AccountCache.value), 11.sp, C.text2, modifier = Modifier.weight(1f))
                Label("v${BuildConfig.VERSION_NAME}", 10.sp, C.text3)
            }
        }
    }
}

@Composable
private fun RailItem(label: String, icon: String, modifier: Modifier = Modifier, dim: Boolean = false, onClick: () -> Unit) {
    Tile(onClick, modifier.fillMaxWidth().height(40.dp), RoundedCornerShape(12.dp), colors = listOf(Color.Transparent), focusColors = C.live, focusScale = 1.03f) {
        Row(Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, 20.dp, if (dim) C.text2 else Color.White, stroke = 1.8f)
            Spacer(Modifier.width(12.dp))
            Label(label, 14.sp, if (dim) C.text2 else C.text, FontWeight.SemiBold)
        }
    }
}

/** Thème « menu latéral » : menu à droite ; bannière publicitaire, « Continuer à regarder » et « Derniers ajouts ». */
@Composable
fun SidebarHome(nav: Navigator, x: Xtream, now: Long) {
    val first = remember { FocusRequester() }
    InitialFocus(first)
    val scope = rememberCoroutineScope()
    val latest by produceState<List<com.kanaani.ibo.data.Movie>?>(null, x) { value = x.latestMovies() }
    Backdrop {
        Row(Modifier.fillMaxSize()) {
            Column(
                Modifier.width(196.dp).fillMaxHeight().background(Color(0xD9060C22)).padding(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 4.dp, bottom = 12.dp)) {
                    Logo(40.dp)
                    Spacer(Modifier.width(8.dp))
                    Text(Brand.name, color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 18.sp, maxLines = 1)
                }
                RailItem(Str.live, Icons.Live, Modifier.focusRequester(first)) { nav.go(Screen.Live()) }
                RailItem(Str.movies, Icons.Movies) { nav.go(Screen.Catalog(series = false)) }
                RailItem(Str.series, Icons.Series) { nav.go(Screen.Catalog(series = true)) }
                RailItem(Str.favorites, Icons.Heart) { nav.go(Screen.Favorites) }
                Spacer(Modifier.weight(1f))
                RailItem(Str.account, Icons.Account, dim = true) { nav.go(Screen.Account) }
                RailItem(Str.settings, Icons.Settings, dim = true) { nav.go(Screen.Settings) }
                Label("v${BuildConfig.VERSION_NAME}", 10.sp, C.text3, modifier = Modifier.padding(start = 12.dp, top = 4.dp))
            }
            Column(Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 14.dp)) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Label(expiryLine(AccountCache.value), 11.sp, C.text2, modifier = Modifier.weight(1f))
                    Text(clock(now), color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 20.sp, maxLines = 1)
                }
                Spacer(Modifier.height(8.dp))
                // Bannière 1920 × 560.
                AdCarousel(Ads.sidebar, Modifier.fillMaxWidth().aspectRatio(1920f / 560f), corner = 16.dp)
                val watching = Store.watching
                if (watching.isNotEmpty()) {
                    Spacer(Modifier.height(14.dp))
                    Label(Str.continueWatching, 15.sp, weight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(4.dp)) {
                        items(watching, key = { it.kind + it.id }) { w ->
                            Tile({ resumeWatch(nav, x, scope, w) }, Modifier.width(170.dp).height(96.dp), RoundedCornerShape(10.dp), colors = listOf(C.card), focusScale = 1.05f) {
                                AsyncImage(w.image, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xDD000000)))))
                                val sub = if (w.kind == "series" && w.episode > 0) "${w.name} · S${w.season} E${w.episode}" else w.name
                                Label(sub, 11.sp, weight = FontWeight.Bold, modifier = Modifier.align(Alignment.BottomStart).padding(start = 8.dp, end = 8.dp, bottom = 9.dp))
                                if (w.progress > 0f) Box(Modifier.align(Alignment.BottomStart).fillMaxWidth(w.progress).height(3.dp).background(Brush.horizontalGradient(C.live)))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))
                Label(Str.latestMovies, 15.sp, weight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                val list = latest
                if (list == null) Box(Modifier.fillMaxWidth().height(150.dp)) { Loading() }
                else LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(4.dp)) {
                    items(list, key = { it.id }) { m ->
                        PosterCard(Poster(m.id, m.name, m.poster, m.rating, movie = m), Modifier.width(96.dp)) { nav.go(Screen.MovieDetail(m)) }
                    }
                }
            }
        }
    }
}
