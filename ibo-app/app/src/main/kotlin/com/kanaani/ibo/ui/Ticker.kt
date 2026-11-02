package com.kanaani.ibo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanaani.ibo.data.Messages
import kotlinx.coroutines.delay

/** Bandeau défilant en bas de l'écran : dernier message ntfy encore valide (voir [Messages]). */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageTicker(top: Screen) {
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) { while (true) { delay(1_000); now = System.currentTimeMillis() } }
    val live = top is Screen.Live || (top is Screen.Play && top.live)
    val msg = Messages.list.lastOrNull { it.until > now && (!it.liveOnly || live) }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        AnimatedVisibility(msg != null, enter = slideInVertically { it } + fadeIn(), exit = slideOutVertically { it } + fadeOut()) {
            val m = msg ?: return@AnimatedVisibility
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxWidth().height(44.dp).clip(RoundedCornerShape(12.dp))
                    .background(Color(0xE6000000)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(Modifier.width(6.dp).fillMaxHeight().background(Brush.verticalGradient(C.live)))
                Spacer(Modifier.width(12.dp))
                Text("📢", fontSize = 18.sp)
                Spacer(Modifier.width(10.dp))
                Text(
                    m.text, color = Color.White, fontFamily = Manrope, fontWeight = FontWeight.Bold, fontSize = 18.sp, maxLines = 1,
                    modifier = Modifier.weight(1f).padding(end = 14.dp)
                        .basicMarquee(iterations = Int.MAX_VALUE, animationMode = MarqueeAnimationMode.Immediately, initialDelayMillis = 800),
                )
            }
        }
    }
}
