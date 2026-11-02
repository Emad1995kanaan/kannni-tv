package com.ultratv.tv.nativeapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.tv.material3.Text
import com.ultratv.tv.nativeapp.BuildConfig
import com.ultratv.tv.nativeapp.data.prefs.UserPreferencesStore
import com.ultratv.tv.nativeapp.data.repo.ProviderRepository
import com.ultratv.tv.nativeapp.data.sync.SyncCoordinator
import com.ultratv.tv.nativeapp.i18n.AppLang
import com.ultratv.tv.nativeapp.i18n.LocalDs
import com.ultratv.tv.nativeapp.i18n.LocalStrings
import com.ultratv.tv.nativeapp.ui.common.RequestInitialFocus
import com.ultratv.tv.nativeapp.ui.common.Toaster
import com.ultratv.tv.nativeapp.ui.common.design
import com.ultratv.tv.nativeapp.ui.design.ChoiceDialog
import com.ultratv.tv.nativeapp.ui.design.DIcon
import com.ultratv.tv.nativeapp.ui.design.FocusSurface
import com.ultratv.tv.nativeapp.ui.design.Icons
import com.ultratv.tv.nativeapp.ui.design.LogoMark
import com.ultratv.tv.nativeapp.ui.design.Manrope
import com.ultratv.tv.nativeapp.ui.design.Sora
import com.ultratv.tv.nativeapp.ui.design.spx
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Actions de l'écran Réglages « IBO ». */
@HiltViewModel
class IboSettingsViewModel @Inject constructor(
    private val prefs: UserPreferencesStore,
    private val providers: ProviderRepository,
    private val sync: SyncCoordinator,
) : ViewModel() {
    val language = prefs.flow

    fun setLanguage(code: String) { viewModelScope.launch { prefs.setLanguage(code) } }

    /** Recharge chaînes, films et séries depuis le serveur. */
    fun refresh(onStarted: () -> Unit) {
        viewModelScope.launch {
            val list = providers.observeProviders().first()
            val p = list.firstOrNull { it.active } ?: list.firstOrNull() ?: return@launch
            sync.request(p.id, force = true)
            onStarted()
        }
    }

    /** Déconnexion : supprime l'abonnement de l'appareil et revient à l'écran de connexion. */
    fun logout() {
        viewModelScope.launch {
            providers.observeProviders().first().forEach { providers.delete(it.id) }
            prefs.resetOnboarding()
        }
    }
}

private fun t(lang: AppLang, en: String, ar: String, he: String) = when (lang) {
    AppLang.Arabic -> ar
    AppLang.Hebrew -> he
    else -> en
}

private val TILE = listOf(Color(0xFF1A2550), Color(0xFF111936))
private val TILE_FOCUS = listOf(Color(0xFF1E5BFF), Color(0xFF0B2E8C))
private val TILE_DANGER = listOf(Color(0xFF7A1C2B), Color(0xFF4A0F1A))

private data class SettingTile(val label: String, val icon: String, val danger: Boolean = false, val onClick: () -> Unit)

/**
 * Réglages « style IBO » : grille de grandes tuiles (langue, abonnement, actualisation, contrôle parental, catégories,
 * déconnexion…) ; « Réglages avancés » ouvre l'écran complet de l'application.
 */
@Composable
fun IboSettingsScreen(onNavigate: (String) -> Unit, vm: IboSettingsViewModel = hiltViewModel()) {
    val S = LocalStrings.current
    val D = LocalDs.current
    val lang = D.lang
    val prefs by vm.language.collectAsState(initial = null)
    var dialog by remember { mutableStateOf<String?>(null) }

    val refreshing = t(lang, "Updating channels, movies and series…", "جارٍ تحديث القنوات والأفلام والمسلسلات…", "מעדכן ערוצים, סרטים וסדרות…")
    val tiles = listOf(
        SettingTile(D.language, Icons.Globe) { dialog = "lang" },
        SettingTile(S.navAccount, Icons.Account) { onNavigate("account") },
        SettingTile(t(lang, "Update content", "تحديث المحتوى", "עדכון תוכן"), Icons.Arrow) { vm.refresh { Toaster.ok(refreshing) } },
        SettingTile(t(lang, "Parental control", "التحكم الأبوي", "בקרת הורים"), Icons.Record) { onNavigate("locked-channels") },
        SettingTile(S.navCategories, Icons.List) { onNavigate("categories") },
        SettingTile(t(lang, "Diagnostics", "فحص الجهاز", "אבחון"), Icons.Info) { onNavigate("diagnostic") },
        SettingTile(t(lang, "Advanced settings", "إعدادات متقدمة", "הגדרות מתקדמות"), Icons.Settings) { onNavigate("settings-advanced") },
        SettingTile(t(lang, "Log out", "تسجيل الخروج", "התנתקות"), Icons.Account, danger = true) { dialog = "logout" },
    )

    val first = remember { FocusRequester() }
    var firstFocused by remember { mutableStateOf(false) }
    RequestInitialFocus(first, hasFocus = { firstFocused })

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF040817), Color(0xFF0A1A46)))),
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = 80.design, vertical = 56.design)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                LogoMark(96)
                Spacer(Modifier.width(28.design))
                Text(S.navSettings, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 56.spx,
                    maxLines = 1, modifier = Modifier.weight(1f))
                Text("v${BuildConfig.VERSION_NAME}", color = Color(0xFF6B7AA6), fontFamily = Manrope, fontSize = 24.spx, maxLines = 1)
            }
            Spacer(Modifier.height(56.design))
            tiles.chunked(4).forEachIndexed { rowIndex, row ->
                Row(Modifier.fillMaxWidth().height(250.design), horizontalArrangement = Arrangement.spacedBy(32.design)) {
                    row.forEachIndexed { i, tile ->
                        val mod = Modifier.weight(1f).height(250.design)
                        Tile(tile, if (rowIndex == 0 && i == 0) mod.focusRequester(first).onFocusChanged { firstFocused = it.isFocused } else mod)
                    }
                }
                Spacer(Modifier.height(32.design))
            }
        }
    }

    when (dialog) {
        "lang" -> ChoiceDialog(D.language, AppLang.PICKER.map { it.code to it.displayName }, prefs?.language,
            { vm.setLanguage(it); dialog = null }, { dialog = null })
        "logout" -> ChoiceDialog(
            t(lang, "Log out of this device?", "تسجيل الخروج من هذا الجهاز؟", "להתנתק מהמכשיר הזה?"),
            listOf(true to t(lang, "Yes, log out", "نعم، تسجيل الخروج", "כן, להתנתק"), false to S.cancel),
            null,
            { yes -> dialog = null; if (yes) vm.logout() },
            { dialog = null },
        )
    }
}

@androidx.compose.runtime.Composable
private fun Tile(t: SettingTile, modifier: Modifier) {
    val shape = RoundedCornerShape(32.design)
    FocusSurface(onClick = t.onClick, modifier = modifier, shape = shape, bg = Color.Transparent, focusedBg = Color.Transparent,
        focusedScale = 1.05f, ringWidth = 5.design) { focused ->
        Box(Modifier.fillMaxSize().background(Brush.linearGradient(if (focused) TILE_FOCUS else if (t.danger) TILE_DANGER else TILE), shape))
        Column(Modifier.fillMaxSize().padding(24.design), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            DIcon(t.icon, 84.design, Color.White, strokeWidth = 1.6f)
            Spacer(Modifier.height(22.design))
            Text(t.label, color = Color.White, fontFamily = Manrope, fontWeight = FontWeight.Bold, fontSize = 30.spx, maxLines = 2,
                overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
        }
    }
}
