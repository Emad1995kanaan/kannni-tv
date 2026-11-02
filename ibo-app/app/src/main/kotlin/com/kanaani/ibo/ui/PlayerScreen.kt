package com.kanaani.ibo.ui

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kanaani.ibo.data.Xtream
import com.kanaani.ibo.player.Playback
import kotlinx.coroutines.delay

@Composable
fun PlayerScreen(nav: Navigator, x: Xtream, s: Screen.Play) {
    val ctx = LocalContext.current
    var index by remember { mutableIntStateOf(s.index) }
    val channel = if (s.live) s.channels.getOrNull(index) else null
    val url = channel?.let { x.liveUrl(it.id) } ?: s.url
    val title = channel?.name ?: s.title
    var showInfo by remember { mutableStateOf(true) }
    var infoTick by remember { mutableIntStateOf(0) }

    LaunchedEffect(url) { Playback.play(ctx, url) }
    // Bandeau d'information : visible 4 s après chaque changement.
    LaunchedEffect(infoTick, index) { showInfo = true; delay(4_000); showInfo = false }
    // Série : épisode suivant à la fin.
    LaunchedEffect(Playback.ended) {
        if (Playback.ended && !s.live && s.index + 1 < s.episodes.size) {
            val next = s.episodes[s.index + 1]
            nav.replaceTop(Screen.Play(s.title.substringBefore(" · ") + " · ${Str.episode} ${next.number}", x.episodeUrl(next), live = false, episodes = s.episodes, index = s.index + 1))
        }
    }

    fun zap(delta: Int) {
        if (s.channels.isEmpty()) return
        index = (index + delta).mod(s.channels.size)
        infoTick++
        // L'écran Direct retrouvera la chaîne en cours dans l'aperçu.
        nav.stack.filterIsInstance<Screen.Live>().lastOrNull()?.let { it.playing = s.channels[index]; it.focusIndex = index }
    }

    val focus = remember { FocusRequester() }
    if (s.live) InitialFocus(focus)

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .then(
                if (s.live) Modifier
                    .focusRequester(focus)
                    .onPreviewKeyEvent { e ->
                        if (e.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false
                        when (e.nativeKeyEvent.keyCode) {
                            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_CHANNEL_UP -> { zap(+1); true }
                            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_CHANNEL_DOWN -> { zap(-1); true }
                            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_INFO -> { infoTick++; true }
                            else -> false
                        }
                    }
                    .focusable()
                else Modifier,
            ),
    ) {
        PlayerSurface(controls = !s.live)
        // Direct au toucher : un appui sur l'image affiche le bandeau (et les boutons ▲ ▼).
        if (s.live) Box(Modifier.fillMaxSize().clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { infoTick++ })

        if (Playback.error) {
            Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Label(Str.playError, 16.sp, C.text, FontWeight.SemiBold)
                Tile({ Playback.retry(ctx) }, Modifier.width(200.dp).height(42.dp), RoundedCornerShape(12.dp), colors = C.live) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Label(Str.retry, 15.sp, weight = FontWeight.Bold) }
                }
            }
        }

        // Direct : bandeau chaîne (numéro, logo, nom) + boutons tactiles ▲ ▼.
        if (s.live && showInfo) {
            Row(
                Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xE6040817))))
                    .padding(horizontal = 28.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Label("${channel?.number?.takeIf { it > 0 } ?: (index + 1)}", 22.sp, weight = FontWeight.Bold, modifier = Modifier.width(56.dp))
                AsyncImage(channel?.logo, null, Modifier.size(46.dp).clip(RoundedCornerShape(8.dp)).background(C.card), contentScale = ContentScale.Fit)
                Spacer(Modifier.width(14.dp))
                Label(title, 20.sp, weight = FontWeight.Bold, modifier = Modifier.weight(1f))
                if (s.channels.size > 1) {
                    ZapButton("▲") { zap(+1) }
                    Spacer(Modifier.width(10.dp))
                    ZapButton("▼") { zap(-1) }
                }
            }
        }
    }
}

@Composable
private fun ZapButton(label: String, onClick: () -> Unit) {
    Box(
        Modifier.size(44.dp).clip(RoundedCornerShape(22.dp)).background(Color(0x66FFFFFF))
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Label(label, 18.sp, weight = FontWeight.Bold) }
}
