package com.kanaani.ibo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kanaani.ibo.Brand
import com.kanaani.ibo.BuildConfig
import com.kanaani.ibo.data.Store
import com.kanaani.ibo.data.Xtream
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var checking by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val userFocus = remember { FocusRequester() }
    InitialFocus(userFocus)

    val submit: () -> Unit = submit@{
        if (checking || user.isBlank() || pass.isEmpty()) return@submit
        checking = true
        error = null
        scope.launch {
            val x = Xtream(Brand.host, user.trim(), pass)
            val acc = x.account()
            val expired = acc?.expiresAt?.let { it < System.currentTimeMillis() } == true
            val status = acc?.status?.lowercase()
            error = when {
                acc == null -> Str.errNetwork
                status == null -> Str.errWrong
                status == "expired" || expired -> Str.errExpired
                status != "active" -> Str.errDisabled
                else -> null
            }
            checking = false
            if (error == null) Store.login(x)
        }
    }

    Backdrop {
        Row(Modifier.fillMaxSize().padding(horizontal = 48.dp, vertical = 32.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Logo(190.dp)
                Spacer(Modifier.height(18.dp))
                Text(Brand.name, color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 32.sp, maxLines = 1)
                Spacer(Modifier.height(6.dp))
                Label(Str.tagline, 14.sp, C.text2)
            }
            Spacer(Modifier.width(36.dp))
            Column(
                Modifier.width(380.dp).clip(RoundedCornerShape(20.dp)).background(Color(0xCC0E1736))
                    .verticalScroll(rememberScrollState()).padding(28.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(Str.signIn, color = C.text, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 26.sp, maxLines = 1)
                Label(Str.signInHint, 12.sp, C.text2, lines = 2)
                Field(user, { user = it; error = null }, Str.username, Modifier.fillMaxWidth(), focusRequester = userFocus)
                Field(pass, { pass = it; error = null }, Str.password, Modifier.fillMaxWidth(), password = true, imeAction = ImeAction.Done, onDone = submit)
                error?.let { Label(it, 13.sp, C.error, FontWeight.SemiBold, lines = 3) }
                Tile(submit, Modifier.fillMaxWidth().height(48.dp), RoundedCornerShape(12.dp), colors = C.live, focusScale = 1.03f) { f ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(if (checking) Str.checking else Str.login, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Lang.entries.forEach { l ->
                        val on = l == Str.lang
                        Tile({ Store.setLanguage(l.code) }, Modifier.weight(1f).height(34.dp), RoundedCornerShape(10.dp),
                            colors = listOf(if (on) C.cardHi else Color.Transparent), focusColors = listOf(C.cardHi)) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Label(l.label, 13.sp, if (on) C.text else C.text2, if (on) FontWeight.Bold else FontWeight.Medium)
                            }
                        }
                    }
                }
                Label("v${BuildConfig.VERSION_NAME}", 11.sp, C.text3)
            }
        }
    }
}
