package com.ultratv.tv.nativeapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.material3.Text
import com.ultratv.tv.nativeapp.Brand
import com.ultratv.tv.nativeapp.BuildConfig
import com.ultratv.tv.nativeapp.i18n.AppLang
import com.ultratv.tv.nativeapp.i18n.LocalDs
import com.ultratv.tv.nativeapp.i18n.LocalStrings
import com.ultratv.tv.nativeapp.i18n.locale
import com.ultratv.tv.nativeapp.ui.account.AccountStrings
import com.ultratv.tv.nativeapp.ui.account.AccountViewModel
import com.ultratv.tv.nativeapp.ui.common.RequestInitialFocus
import com.ultratv.tv.nativeapp.ui.common.design
import com.ultratv.tv.nativeapp.ui.design.DIcon
import com.ultratv.tv.nativeapp.ui.design.FocusSurface
import com.ultratv.tv.nativeapp.ui.design.Icons
import com.ultratv.tv.nativeapp.ui.design.LogoMark
import com.ultratv.tv.nativeapp.ui.design.Manrope
import com.ultratv.tv.nativeapp.ui.design.Sora
import com.ultratv.tv.nativeapp.ui.design.spx
import kotlinx.coroutines.delay

/**
 * Accueil « style IBO » (BRAND_HOME_STYLE=ibo) : grandes tuiles Direct / Films / Séries, une rangée de raccourcis,
 * l'heure en haut et l'échéance de l'abonnement en bas. Les écrans de destination sont ceux de l'app.
 */

private val BG_TOP = Color(0xFF040817)
private val BG_BOTTOM = Color(0xFF0A1A46)
private val GLOW = Color(0x553A6BFF)
private val TILE_LIVE = listOf(Color(0xFF1E5BFF), Color(0xFF0B2E8C))
private val TILE_MOVIES = listOf(Color(0xFFFF6A3D), Color(0xFFB3122E))
private val TILE_SERIES = listOf(Color(0xFF8B5CF6), Color(0xFF3B1C8C))
private val TILE_SMALL = listOf(Color(0xFF1A2550), Color(0xFF111936))

/** Libellés propres à cet écran (arabe, anglais, hébreu ; les autres langues retombent sur l'anglais). */
private fun iboText(lang: AppLang, en: String, ar: String, he: String) = when (lang) {
    AppLang.Arabic -> ar
    AppLang.Hebrew -> he
    else -> en
}

@Composable
fun IboHomeScreen(
    onGoLive: () -> Unit,
    onGoMovies: () -> Unit,
    onGoSeries: () -> Unit,
    onGoGuide: () -> Unit,
    onGoFavorites: () -> Unit,
    onGoSearch: () -> Unit,
    onGoAccount: () -> Unit,
    onGoSettings: () -> Unit,
) {
    val S = LocalStrings.current
    val D = LocalDs.current
    val accountVm: AccountViewModel = hiltViewModel()
    val account by accountVm.state.collectAsStateWithLifecycle()
    val accountStrings = AccountStrings(D.lang)

    // Horloge : rafraîchie chaque minute.
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            now = System.currentTimeMillis()
            delay(60_000L - now % 60_000L)
        }
    }
    val locale = D.locale
    val time = remember(now, locale) { java.text.SimpleDateFormat("HH:mm", locale).format(java.util.Date(now)) }
    val date = remember(now, locale) { java.text.SimpleDateFormat("EEEE d MMMM", locale).format(java.util.Date(now)) }

    val expiry: String? = account.account?.let { a ->
        when (val exp = a.expiresAt) {
            null -> accountStrings.never
            else -> {
                val days = ((exp - System.currentTimeMillis()).coerceAtLeast(0) + 86_399_999L) / 86_400_000L
                val d = java.text.SimpleDateFormat("d MMM yyyy", locale).format(java.util.Date(exp))
                "${accountStrings.expires} $d · $days ${accountStrings.daysLeft}"
            }
        }
    }

    val big = listOf(
        TileSpec(S.navLive, Icons.Live, TILE_LIVE, onGoLive),
        TileSpec(S.navMovies, Icons.Movies, TILE_MOVIES, onGoMovies),
        TileSpec(S.navSeries, Icons.Series, TILE_SERIES, onGoSeries),
    )
    val small = listOf(
        TileSpec(S.navGuide, Icons.Guide, TILE_SMALL, onGoGuide),
        TileSpec(S.navFavorites, Icons.Heart, TILE_SMALL, onGoFavorites),
        TileSpec(S.navSearch, Icons.Search, TILE_SMALL, onGoSearch),
        TileSpec(S.navAccount, Icons.Account, TILE_SMALL, onGoAccount),
        TileSpec(S.navSettings, Icons.Settings, TILE_SMALL, onGoSettings),
    )
    val welcome = iboText(D.lang, "Welcome", "أهلاً وسهلاً", "ברוכים הבאים")

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(BG_TOP, BG_BOTTOM)))
            .background(Brush.radialGradient(listOf(GLOW, Color.Transparent))),
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            if (maxWidth < maxHeight) {
                PortraitLayout(big, small, time, date, welcome, expiry)
            } else {
                LandscapeLayout(big, small, time, date, welcome, expiry)
            }
        }
    }
}

private data class TileSpec(val label: String, val icon: String, val colors: List<Color>, val onClick: () -> Unit)

/** Téléviseur / tablette en paysage : tout tient sur un écran, sans défilement. */
@Composable
private fun LandscapeLayout(big: List<TileSpec>, small: List<TileSpec>, time: String, date: String, welcome: String, expiry: String?) {
    val first = remember { FocusRequester() }
    var firstFocused by remember { mutableStateOf(false) }
    RequestInitialFocus(first, hasFocus = { firstFocused })
    Column(Modifier.fillMaxSize().padding(horizontal = 80.design, vertical = 56.design)) {
        Header(time, date, welcome, logo = 104, timeSize = 64.spx, small = false)
        Spacer(Modifier.height(48.design))
        Row(Modifier.fillMaxWidth().height(470.design), horizontalArrangement = Arrangement.spacedBy(36.design)) {
            big.forEachIndexed { i, t ->
                val mod = Modifier.weight(if (i == 0) 1.3f else 1f).height(470.design)
                BigTile(
                    t,
                    if (i == 0) mod.focusRequester(first).onFocusChanged { firstFocused = it.isFocused } else mod,
                    iconSize = 150.design, textSize = 46.spx,
                )
            }
        }
        Spacer(Modifier.height(36.design))
        Row(Modifier.fillMaxWidth().height(150.design), horizontalArrangement = Arrangement.spacedBy(28.design)) {
            small.forEach { t -> SmallTile(t, Modifier.weight(1f).height(150.design), iconSize = 52.design, textSize = 26.spx) }
        }
        Spacer(Modifier.weight(1f))
        Footer(expiry, 24.spx)
    }
}

/** Téléphone en portrait : tuiles empilées, défilement vertical. */
@Composable
private fun PortraitLayout(big: List<TileSpec>, small: List<TileSpec>, time: String, date: String, welcome: String, expiry: String?) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Header(time, date, welcome, logo = 112, timeSize = 30.sp, small = true)
        big.forEach { t -> BigTile(t, Modifier.fillMaxWidth().height(132.dp), iconSize = 64.dp, textSize = 26.sp, horizontal = true) }
        small.chunked(3).forEach { rowItems ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowItems.forEach { t -> SmallTile(t, Modifier.weight(1f).height(96.dp), iconSize = 28.dp, textSize = 14.sp) }
                repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
            }
        }
        Footer(expiry, 13.sp)
    }
}

@Composable
private fun Header(time: String, date: String, welcome: String, logo: Int, timeSize: TextUnit, small: Boolean) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        LogoMark(logo)
        Spacer(Modifier.width(if (small) 12.dp else 28.design))
        Column(Modifier.weight(1f)) {
            Text(Brand.providerName.ifBlank { welcome }, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold,
                fontSize = if (small) 22.sp else 44.spx, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(welcome, color = Color(0xFFB8C4E6), fontFamily = Manrope, fontSize = if (small) 13.sp else 24.spx, maxLines = 1)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(time, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = timeSize, maxLines = 1)
            Text(date, color = Color(0xFFB8C4E6), fontFamily = Manrope, fontSize = if (small) 12.sp else 22.spx, maxLines = 1)
        }
    }
}

@Composable
private fun BigTile(t: TileSpec, modifier: Modifier, iconSize: Dp, textSize: TextUnit, horizontal: Boolean = false) {
    val shape = RoundedCornerShape(if (horizontal) 20.dp else 36.design)
    FocusSurface(onClick = t.onClick, modifier = modifier, shape = shape, bg = Color.Transparent, focusedBg = Color.Transparent,
        focusedScale = 1.05f, ringWidth = 6.design) { focused ->
        Box(Modifier.fillMaxSize().background(Brush.linearGradient(t.colors), shape))
        if (focused) Box(Modifier.fillMaxSize().background(Color.White.copy(alpha = 0.12f), shape))
        if (horizontal) {
            Row(Modifier.fillMaxSize().padding(horizontal = 24.dp), verticalAlignment = Alignment.CenterVertically) {
                DIcon(t.icon, iconSize, Color.White, strokeWidth = 1.6f)
                Spacer(Modifier.width(20.dp))
                Text(t.label, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = textSize, maxLines = 1)
            }
        } else {
            Column(Modifier.fillMaxSize().padding(36.design), verticalArrangement = Arrangement.SpaceBetween) {
                DIcon(t.icon, iconSize, Color.White, strokeWidth = 1.4f)
                Text(t.label, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = textSize,
                    maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
private fun SmallTile(t: TileSpec, modifier: Modifier, iconSize: Dp, textSize: TextUnit) {
    val shape = RoundedCornerShape(24.design)
    FocusSurface(onClick = t.onClick, modifier = modifier, shape = shape, bg = Color.Transparent, focusedBg = Color.Transparent,
        focusedScale = 1.06f, ringWidth = 5.design) { focused ->
        Box(Modifier.fillMaxSize().background(Brush.linearGradient(if (focused) TILE_LIVE else t.colors), shape))
        Column(Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            DIcon(t.icon, iconSize, Color.White, strokeWidth = 1.8f)
            Spacer(Modifier.height(8.dp))
            Text(t.label, color = Color.White, fontFamily = Manrope, fontWeight = FontWeight.SemiBold, fontSize = textSize,
                maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun Footer(expiry: String?, size: TextUnit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(expiry.orEmpty(), color = Color(0xFFB8C4E6), fontFamily = Manrope, fontSize = size, maxLines = 1,
            overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
        Text("v${BuildConfig.VERSION_NAME}", color = Color(0xFF6B7AA6), fontFamily = Manrope, fontSize = size, maxLines = 1)
    }
}
