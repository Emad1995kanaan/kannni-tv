package com.ultratv.tv.nativeapp.ui.onboarding

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.Text
import com.ultratv.tv.nativeapp.Brand
import com.ultratv.tv.nativeapp.BuildConfig
import com.ultratv.tv.nativeapp.data.db.ProviderEntity
import com.ultratv.tv.nativeapp.data.prefs.UserPreferencesStore
import com.ultratv.tv.nativeapp.data.repo.ProviderRepository
import com.ultratv.tv.nativeapp.data.sync.SyncCoordinator
import com.ultratv.tv.nativeapp.data.xtream.XtreamClient
import com.ultratv.tv.nativeapp.i18n.AppLang
import com.ultratv.tv.nativeapp.i18n.LocalDs
import com.ultratv.tv.nativeapp.i18n.LocalStrings
import com.ultratv.tv.nativeapp.ui.common.design
import com.ultratv.tv.nativeapp.ui.design.FocusSurface
import com.ultratv.tv.nativeapp.ui.design.LogoMark
import com.ultratv.tv.nativeapp.ui.design.Manrope
import com.ultratv.tv.nativeapp.ui.design.Sora
import com.ultratv.tv.nativeapp.ui.design.Ux
import com.ultratv.tv.nativeapp.ui.design.spx
import com.ultratv.tv.nativeapp.ui.settings.FormField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Résultat de la dernière tentative de connexion. */
enum class IboLoginState { Idle, Checking, WrongCredentials, Expired, Disabled }

/**
 * Connexion « style IBO » (version IBO, serveur fixé par la marque) : identifiant + mot de passe vérifiés auprès du
 * serveur Xtream AVANT d'enregistrer la source ; ensuite toutes les langues sont retenues et la synchro démarre.
 */
@HiltViewModel
class IboLoginViewModel @Inject constructor(
    private val xtream: XtreamClient,
    private val providers: ProviderRepository,
    private val prefs: UserPreferencesStore,
    private val sync: SyncCoordinator,
) : ViewModel() {
    private val _state = MutableStateFlow(IboLoginState.Idle)
    val state: StateFlow<IboLoginState> = _state

    fun login(username: String, password: String) {
        if (_state.value == IboLoginState.Checking) return
        val user = username.trim()
        if (user.isEmpty() || password.isEmpty()) return
        _state.value = IboLoginState.Checking
        viewModelScope.launch {
            val probe = ProviderEntity(name = Brand.providerName, kind = "XTREAM", baseUrl = Brand.host, username = user, password = password)
            val acc = xtream.fetchAccount(probe)
            val status = acc?.status?.lowercase()
            val expired = acc?.expiresAt?.let { it < System.currentTimeMillis() } == true
            _state.value = when {
                acc == null || status == null -> IboLoginState.WrongCredentials
                status == "expired" || expired -> IboLoginState.Expired
                status != "active" -> IboLoginState.Disabled
                else -> {
                    val id = providers.addXtream(Brand.providerName, Brand.host, user, password)
                    if (providers.firstActive() == null) providers.setDefault(id)
                    prefs.setLanguages("")          // toutes les langues : aucune étape de choix
                    sync.request(id, force = true)
                    prefs.markOnboardingSeen()      // l'application passe à l'écran de première synchro
                    IboLoginState.Idle
                }
            }
        }
    }

    fun setLanguage(code: String) { viewModelScope.launch { prefs.setLanguage(code) } }
}

private fun loginText(lang: AppLang, en: String, ar: String, he: String) = when (lang) {
    AppLang.Arabic -> ar
    AppLang.Hebrew -> he
    else -> en
}

@Composable
fun IboLoginScreen(vm: IboLoginViewModel = hiltViewModel()) {
    val S = LocalStrings.current
    val lang = LocalDs.current.lang
    val state by vm.state.collectAsState()
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val checking = state == IboLoginState.Checking
    val canSubmit = user.isNotBlank() && pass.isNotEmpty() && !checking

    val title = loginText(lang, "Sign in", "تسجيل الدخول", "התחברות")
    val subtitle = loginText(lang, "Enter the username and password of your subscription",
        "أدخل اسم المستخدم وكلمة المرور الخاصة باشتراكك", "הזינו את שם המשתמש והסיסמה של המנוי שלכם")
    val button = if (checking) loginText(lang, "Checking…", "جارٍ التحقق…", "בודק…") else loginText(lang, "Login", "دخول", "כניסה")
    val error = when (state) {
        IboLoginState.WrongCredentials -> loginText(lang,
            "Could not sign in. Check your username, password and internet connection.",
            "تعذّر تسجيل الدخول. تأكد من اسم المستخدم وكلمة المرور واتصال الإنترنت.",
            "לא ניתן להתחבר. בדקו את שם המשתמש, הסיסמה וחיבור האינטרנט.")
        IboLoginState.Expired -> loginText(lang,
            "Your subscription has expired. Contact your reseller to renew it.",
            "انتهى اشتراكك. تواصل مع الموزّع لتجديده.",
            "המנוי שלכם הסתיים. פנו למשווק כדי לחדש אותו.")
        IboLoginState.Disabled -> loginText(lang,
            "Your subscription is suspended. Contact your reseller.",
            "اشتراكك موقوف. تواصل مع الموزّع.",
            "המנוי שלכם מושהה. פנו למשווק.")
        else -> null
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF040817), Color(0xFF0A1A46))))
            .background(Brush.radialGradient(listOf(Color(0x553A6BFF), Color.Transparent))),
    ) {
        Row(Modifier.fillMaxSize().padding(horizontal = 96.design, vertical = 64.design), verticalAlignment = Alignment.CenterVertically) {
            // Marque
            Column(Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                LogoMark(420)
                Spacer(Modifier.height(36.design))
                Text(Brand.providerName, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 64.spx, maxLines = 1)
                Spacer(Modifier.height(12.design))
                Text(loginText(lang, "Live TV · Movies · Series", "بث مباشر · أفلام · مسلسلات", "שידור חי · סרטים · סדרות"),
                    color = Color(0xFFB8C4E6), fontFamily = Manrope, fontSize = 28.spx, maxLines = 1)
            }
            Spacer(Modifier.width(72.design))
            // Formulaire
            Column(
                Modifier
                    .width(760.design)
                    .clip(RoundedCornerShape(36.design))
                    .background(Color(0xCC0E1736))
                    .verticalScroll(rememberScrollState())
                    .padding(56.design),
                verticalArrangement = Arrangement.spacedBy(24.design),
            ) {
                Text(title, color = Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 52.spx, maxLines = 1)
                Text(subtitle, color = Color(0xFFB8C4E6), fontFamily = Manrope, fontSize = 24.spx, maxLines = 2)
                FormField(S.fieldUsername, user, { user = it }, autoFocus = true, required = true, testTag = "ibo-user")
                FormField(S.fieldPassword, pass, { pass = it }, password = true, last = true, required = true, placeholder = "••••••••", testTag = "ibo-pass")
                if (error != null) Text(error, color = Ux.Err, fontFamily = Manrope, fontWeight = FontWeight.SemiBold, fontSize = 24.spx, maxLines = 3)
                FocusSurface(
                    onClick = { if (canSubmit) vm.login(user, pass) },
                    shape = RoundedCornerShape(22.design),
                    bg = if (canSubmit) Color(0xFF1E5BFF) else Color(0xFF2B3A72),
                    focusedBg = Color.White,
                    focusedScale = 1.03f,
                    modifier = Modifier.fillMaxWidth().height(92.design),
                ) { f ->
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(button, color = if (f) Color(0xFF0A1A46) else Color.White, fontFamily = Sora, fontWeight = FontWeight.Bold, fontSize = 34.spx, maxLines = 1)
                    }
                }
                // Langue de l'interface (arabe / anglais / hébreu)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.design)) {
                    AppLang.PICKER.forEach { l ->
                        val on = l == lang
                        FocusSurface(
                            onClick = { vm.setLanguage(l.code) },
                            shape = RoundedCornerShape(18.design),
                            bg = if (on) Color(0xFF213063) else Color.Transparent,
                            focusedScale = 1.04f, ringWidth = 4.design,
                            modifier = Modifier.weight(1f).height(64.design),
                        ) { f ->
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(l.displayName, color = if (f) Color(0xFF0A1A46) else Color.White, fontFamily = Manrope,
                                    fontWeight = if (on) FontWeight.Bold else FontWeight.Medium, fontSize = 24.spx, maxLines = 1, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
                Text("v${BuildConfig.VERSION_NAME}", color = Color(0xFF6B7AA6), fontFamily = Manrope, fontSize = 20.spx, maxLines = 1)
            }
        }
    }
}
