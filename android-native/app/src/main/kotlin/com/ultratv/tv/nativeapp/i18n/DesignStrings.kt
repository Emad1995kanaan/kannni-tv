package com.ultratv.tv.nativeapp.i18n

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

/**
 * Textes des écrans « Claude Design », EN / FR / ES / AR côte à côte (une ligne = une chaîne,
 * impossible d'oublier une langue). Complète [Strings]. Aucune donnée factice : ce ne sont que
 * des libellés d'interface.
 */
class DesignStrings(val lang: AppLang) {
    private fun t(en: String, fr: String, es: String = en, ar: String = en, he: String = en): String = when (lang) {
        AppLang.French -> fr
        AppLang.Spanish -> es
        AppLang.Arabic -> ar
        AppLang.Hebrew -> he
        else -> en
    }

    // Rail
    val railProfile get() = t("Profile", "Profil", "Perfil", "الملف الشخصي", he = "פרופיל")
    val railSwitchProfile get() = t("Switch profile", "Changer de profil", "Cambiar de perfil", "تبديل الملف", he = "החלפת פרופיל")

    // Fiches film / série
    val castTitle get() = t("Cast", "Distribution", "Reparto", "طاقم التمثيل", he = "שידור למסך")
    fun resumeAt(time: String) = when (lang) { AppLang.French -> "Reprendre à $time"; AppLang.Spanish -> "Reanudar en $time"; AppLang.Arabic -> "استئناف من $time"; AppLang.Hebrew -> "המשך מ-$time"; else -> "Resume at $time" }

    fun seasonLabel(n: Int) = when (lang) { AppLang.French -> "Saison $n"; AppLang.Spanish -> "Temporada $n"; AppLang.Arabic -> "الموسم $n"; AppLang.Hebrew -> "עונה $n"; else -> "Season $n" }
    fun seasonsCount(n: Int) = when (lang) { AppLang.French -> if (n <= 1) "$n saison" else "$n saisons"; AppLang.Spanish -> if (n == 1) "$n temporada" else "$n temporadas"; AppLang.Arabic -> "$n مواسم"; AppLang.Hebrew -> if (n == 1) "עונה $n" else "$n עונות"; else -> if (n == 1) "$n season" else "$n seasons" }
    fun episodeTag(s: Int, e: Int) = when (lang) { AppLang.French -> "S$s É$e"; AppLang.Arabic -> "م$s ح$e"; AppLang.Hebrew -> "ע$s פ$e"; else -> "S$s E$e" }
    fun episodeWord(e: Int) = when (lang) { AppLang.French -> "Épisode $e"; AppLang.Spanish -> "Episodio $e"; AppLang.Arabic -> "الحلقة $e"; AppLang.Hebrew -> "פרק $e"; else -> "Episode $e" }
    fun episodeShort(e: Int) = when (lang) { AppLang.French -> "É$e"; AppLang.Arabic -> "ح$e"; AppLang.Hebrew -> "פ$e"; else -> "E$e" }
    fun resumeEpisode(s: Int, e: Int) = when (lang) { AppLang.French -> "Reprendre ${episodeTag(s, e)}"; AppLang.Spanish -> "Reanudar ${episodeTag(s, e)}"; AppLang.Arabic -> "استئناف ${episodeTag(s, e)}"; AppLang.Hebrew -> "המשך ${episodeTag(s, e)}"; else -> "Resume ${episodeTag(s, e)}" }
    fun playEpisodeLabel(s: Int, e: Int) = when (lang) { AppLang.French -> "Lecture ${episodeTag(s, e)}"; AppLang.Spanish -> "Ver ${episodeTag(s, e)}"; AppLang.Arabic -> "تشغيل ${episodeTag(s, e)}"; AppLang.Hebrew -> "הפעלה ${episodeTag(s, e)}"; else -> "Play ${episodeTag(s, e)}" }
    val watchedLabel get() = t("watched", "vu", "visto", "تمت المشاهدة", he = "נצפה")
    fun remainingMin(m: Int) = when (lang) { AppLang.French -> "reste $m min"; AppLang.Spanish -> "quedan $m min"; AppLang.Arabic -> "متبقّي $m د"; AppLang.Hebrew -> "נותרו $m דק׳"; else -> "$m min left" }

    // Recherche
    val keySpace get() = t("Space", "Espace", "Espacio", "مسافة", he = "רווח")
    val keyVoice get() = t("Voice", "Voix", "Voz", "صوت", he = "קולי")
    val keyDelete get() = t("Delete", "Suppr.", "Borrar", "حذف", he = "מחיקה")
    val searchStart get() = t("Start typing to search channels, movies and series.", "Commencez à taper pour chercher chaînes, films et séries.", "Empieza a escribir para buscar canales, películas y series.", "ابدأ الكتابة للبحث عن القنوات والأفلام والمسلسلات.", he = "התחילו להקליד כדי לחפש ערוצים, סרטים וסדרות.")
    fun searchChannels(n: Int) = when (lang) { AppLang.French -> "CHAÎNES · $n"; AppLang.Spanish -> "CANALES · $n"; AppLang.Arabic -> "القنوات · $n"; AppLang.Hebrew -> "ערוצים · $n"; else -> "CHANNELS · $n" }
    fun searchPrograms(n: Int) = when (lang) { AppLang.French -> "AU PROGRAMME · $n"; AppLang.Spanish -> "EN PROGRAMACIÓN · $n"; AppLang.Arabic -> "على البرنامج · $n"; AppLang.Hebrew -> "בלוח השידורים · $n"; else -> "ON THE SCHEDULE · $n" }
    val programNow get() = t("On now", "En cours", "En curso", "جارٍ الآن", he = "משודר עכשיו")
    fun searchVod(n: Int) = when (lang) { AppLang.French -> "FILMS ET SÉRIES · $n"; AppLang.Spanish -> "PELÍCULAS Y SERIES · $n"; AppLang.Arabic -> "الأفلام والمسلسلات · $n"; AppLang.Hebrew -> "סרטים וסדרות · $n"; else -> "MOVIES AND SERIES · $n" }

    // Favoris
    val channelsWord get() = t("Channels", "Chaînes", "Canales", "القنوات", he = "ערוצים")
    fun favTab(label: String, n: Int) = "$label · $n"
    val favHint get() = t("Long-press OK: remove from favorites", "Appui long sur OK : retirer des favoris", "Pulsación larga en OK: quitar de favoritos", "اضغط مطولًا على OK لإزالة من المفضلة", he = "לחיצה ארוכה על OK: הסרה מהמועדפים")

    // Enregistrements
    val storageUsed get() = t("Storage used", "Stockage utilisé", "Almacenamiento usado", "التخزين المستخدم", he = "נפח בשימוש")
    fun storageOf(used: String, total: String) = when (lang) { AppLang.French -> "$used sur $total"; AppLang.Spanish -> "$used de $total"; AppLang.Arabic -> "$used من $total"; AppLang.Hebrew -> "$used מתוך $total"; else -> "$used of $total" }
    val recActive get() = t("In progress and scheduled", "En cours et programmés", "En curso y programadas", "قيد التنفيذ ومجدولة", he = "בתהליך ומתוזמנות")
    val recDone get() = t("Finished", "Terminés", "Terminadas", "المكتملة", he = "הסתיימו")
    val recBadge get() = t("REC", "REC", "REC", "تسجيل", he = "הקלטה")
    val recQueuedBadge get() = t("QUEUE", "FILE", "COLA", "انتظار", he = "בתור")
    val recFailedBadge get() = t("FAILED", "ÉCHEC", "ERROR", "فشل", he = "נכשל")
    val recStop get() = t("Stop", "Arrêter", "Detener", "إيقاف", he = "עצירה")
    val recCancel get() = t("Cancel", "Annuler", "Cancelar", "إلغاء", he = "ביטול")

    // Chaînes verrouillées
    val lockedSwitch get() = t("Locked", "Verrouillée", "Bloqueado", "مقفلة", he = "נעול")
    val lockedOnly get() = t("LOCKED CHANNELS", "CHAÎNES VERROUILLÉES", "CANALES BLOQUEADOS", "القنوات المقفلة", he = "ערוצים נעולים")
    val lockedNone get() = t("No locked channel. Search for a channel above to lock it.", "Aucune chaîne verrouillée. Cherchez une chaîne ci-dessus pour la verrouiller.", "Ningún canal bloqueado. Busca un canal arriba para bloquearlo.", "لا توجد قنوات مقفلة. ابحث عن قناة أعلاه لقفلها.", he = "אין ערוצים נעולים. חפשו ערוץ למעלה כדי לנעול אותו.")
    fun lockedCountLine(n: Int) = when (lang) { AppLang.French -> if (n <= 1) "$n chaîne verrouillée" else "$n chaînes verrouillées"; AppLang.Spanish -> "$n canales bloqueados"; AppLang.Arabic -> "$n قناة مقفلة"; AppLang.Hebrew -> if (n == 1) "ערוץ נעול אחד" else "$n ערוצים נעולים"; else -> if (n == 1) "$n locked channel" else "$n locked channels" }
    fun searchResultsCount(n: Int) = when (lang) { AppLang.French -> "RÉSULTATS · $n"; AppLang.Spanish -> "RESULTADOS · $n"; AppLang.Arabic -> "النتائج · $n"; AppLang.Hebrew -> "תוצאות · $n"; else -> "RESULTS · $n" }

    // Formulaires d'ajout de source
    val addAndSync get() = t("Add and sync", "Ajouter et synchroniser", "Añadir y sincronizar", "إضافة ومزامنة", he = "הוספה וסנכרון")
    val fieldRequired get() = t("Required field", "Champ requis", "Campo obligatorio", "حقل مطلوب", he = "שדה חובה")
    val formNamePh get() = t("e.g. Living room", "Ex. Salon", "Ej. Salón", "مثال: غرفة المعيشة", he = "למשל: סלון")
    val formSubXtream get() = t("The details provided by your operator", "Les informations fournies par votre opérateur", "Los datos que te da tu operador", "المعلومات التي يقدمها مشغّلك", he = "הפרטים שקיבלתם מהספק")
    val xtreamRecognized get() = t("Address recognized: Xtream Codes source", "Adresse reconnue : source Xtream Codes", "Dirección reconocida: fuente Xtream Codes", "تم التعرف على العنوان: مصدر Xtream Codes", he = "הכתובת זוהתה: מקור Xtream Codes")
    val switchToXtream get() = t("Switch to Xtream Codes", "Passer en Xtream Codes", "Cambiar a Xtream Codes", "التحويل إلى Xtream Codes", he = "מעבר ל-Xtream Codes")
    val cardCloud get() = t("From the cloud", "Depuis le cloud", "Desde la nube", "من السحابة", he = "מהענן")
    val cardCloudDesc get() = t("Sync your sources from the dashboard with a short code", "Synchronisez vos sources depuis le tableau de bord avec un code", "Sincroniza tus fuentes desde el panel con un código", "زامن مصادرك من لوحة التحكم برمز قصير", he = "סנכרון המקורות מלוח הבקרה עם קוד קצר")
    val dashboardTitle get() = t("Dashboard", "Tableau de bord", "Panel de control", "لوحة التحكم", he = "לוח בקרה")
    val categoryLoading get() = t("Loading…", "Chargement…", "Cargando…", "جارٍ التحميل…", he = "טוען…")
    val recentlyWatched get() = t("Recently watched", "Dernières chaînes regardées", "Vistos recientemente", "شوهدت مؤخرًا", he = "נצפו לאחרונה")
    val latestMovies get() = t("Latest movies", "Derniers films ajoutés", "Últimas películas", "أحدث الأفلام", he = "סרטים אחרונים")
    val latestSeries get() = t("Latest series", "Dernières séries ajoutées", "Últimas series", "أحدث المسلسلات", he = "סדרות אחרונות")
    /** Nom de la chaîne d'accueil Google TV des derniers ajouts. */
    // Sans « Ultra TV · » : Google TV et les lanceurs (Kairo…) affichent déjà l'appli à côté (« Ultra TV · Nouveautés · UTV »),
    // et la chaîne Favoris n'avait pas ce préfixe.
    val newsChannelName get() = t("New releases", "Nouveautés", "Novedades", "الجديد", he = "חדש")
    val seeAll get() = t("See all", "Voir tout", "Ver todo", "عرض الكل", he = "הצג הכול")
    val loadingLabel get() = t("Loading…", "Chargement…", "Cargando…", "جارٍ التحميل…", he = "טוען…")
    val searchingLabel get() = t("Searching…", "Recherche…", "Buscando…", "جارٍ البحث…", he = "מחפש…")
    val dashboardScan get() = t("Scan with your phone", "À scanner avec votre téléphone", "Escanea con tu teléfono", "امسح الرمز بهاتفك", he = "סרקו עם הטלפון")
    val pairScanAuto get() = t("Scan with your phone to pair automatically", "Scannez avec votre téléphone pour appairer automatiquement", "Escanea con tu teléfono para vincular automáticamente", "امسح الرمز بهاتفك للاقتران تلقائيًا", he = "סרקו עם הטלפון לצימוד אוטומטי")
    val pairDeviceLabel get() = t("Name shown on the dashboard", "Nom affiché sur le tableau de bord", "Nombre mostrado en el panel", "الاسم الظاهر في لوحة التحكم", he = "השם שיוצג בלוח הבקרה")
    val searchPill get() = t("Search", "Rechercher", "Buscar", "بحث", he = "חיפוש")
    val unsupportedSource get() = t("Unsupported source type", "Type de source non pris en charge", "Tipo de fuente no compatible", "نوع المصدر غير مدعوم", he = "סוג מקור לא נתמך")
    val unsupportedSourceHint get() = t("This source type is no longer supported. You can delete it.", "Ce type de source n’est plus pris en charge. Vous pouvez le supprimer.", "Este tipo de fuente ya no es compatible. Puede eliminarla.", "لم يعد هذا النوع من المصادر مدعومًا. يمكنك حذفه.", he = "סוג המקור הזה כבר לא נתמך. אפשר למחוק אותו.")
    val formSubM3u get() = t("The address of your playlist", "L’adresse de votre liste de lecture", "La dirección de tu lista", "عنوان قائمة التشغيل", he = "כתובת רשימת ההשמעה שלכם")
    val formHintFields get() = t("▲▼ Previous / next field", "▲▼ Champ précédent / suivant", "▲▼ Campo anterior / siguiente", "▲▼ الحقل السابق / التالي", he = "▲▼ שדה קודם / הבא")
    val formHintIme get() = t("Keyboard: Next", "Clavier : Suivant", "Teclado: Siguiente", "لوحة المفاتيح: التالي", he = "מקלדת: הבא")
    val formHintBack get() = t("‹ Back to cancel", "‹ Retour pour annuler", "‹ Atrás para cancelar", "‹ رجوع للإلغاء", he = "‹ חזרה לביטול")

    // Appairage
    val pairEyebrow get() = t("SYNC FROM THE CLOUD", "SYNCHRONISER DEPUIS LE CLOUD", "SINCRONIZAR DESDE LA NUBE", "المزامنة من السحابة", he = "סנכרון מהענן")
    val pairTitle get() = t("Add your sources from your phone", "Ajoutez vos sources depuis votre téléphone", "Añade tus fuentes desde tu móvil", "أضف مصادرك من هاتفك", he = "הוסיפו מקורות מהטלפון")
    fun pairStep1(host: String) = when (lang) { AppLang.French -> "Ouvrez $host"; AppLang.Spanish -> "Abre $host"; AppLang.Arabic -> "افتح $host"; AppLang.Hebrew -> "פתחו את $host"; else -> "Open $host" }.trim()
    val pairStep2 get() = t("Sign in, then “Pair a TV”", "Connectez-vous, puis « Appairer une TV »", "Inicia sesión y elige «Vincular una TV»", "سجّل الدخول ثم «إقران تلفاز»", he = "התחברו, ואז „צימוד טלוויזיה”")
    val pairStep3 get() = t("Enter the code shown here", "Saisissez le code ci-contre", "Introduce el código mostrado aquí", "أدخل الرمز المعروض هنا", he = "הזינו את הקוד שמוצג כאן")
    val pairNewCode get() = t("New code", "Nouveau code", "Código nuevo", "رمز جديد", he = "קוד חדש")
    val pairYourCode get() = t("YOUR CODE", "VOTRE CODE", "TU CÓDIGO", "رمزك", he = "הקוד שלכם")
    val pairRequesting get() = t("Requesting a code…", "Demande d’un code…", "Solicitando un código…", "جارٍ طلب الرمز…", he = "מבקש קוד…")
    fun pairWaiting(remaining: String) = when (lang) { AppLang.French -> "En attente de saisie · expire dans $remaining"; AppLang.Spanish -> "Esperando · caduca en $remaining"; AppLang.Arabic -> "بانتظار الإدخال · ينتهي خلال $remaining"; AppLang.Hebrew -> "ממתין להזנה · פג תוקף בעוד $remaining"; else -> "Waiting for entry · expires in $remaining" }
    fun pairDevice(name: String) = when (lang) { AppLang.French -> "Appareil : $name"; AppLang.Spanish -> "Dispositivo: $name"; AppLang.Arabic -> "الجهاز: $name"; AppLang.Hebrew -> "מכשיר: $name"; else -> "Device: $name" }
    val pairFailed get() = t("Pairing failed or the code expired. Try a new code.", "L’appairage a échoué ou le code a expiré. Demandez un nouveau code.", "El emparejamiento falló o el código caducó. Pide un código nuevo.", "فشل الإقران أو انتهت صلاحية الرمز. اطلب رمزًا جديدًا.", he = "הצימוד נכשל או שהקוד פג. נסו קוד חדש.")
    val workerUrlTitle get() = t("Dashboard address", "Adresse du tableau de bord", "Dirección del panel", "عنوان لوحة التحكم", he = "כתובת לוח הבקרה")
    val workerUrlSub get() = t("The secure (https) address used to pair this TV", "L’adresse sécurisée (https) utilisée pour appairer cette TV", "La dirección segura (https) para vincular esta TV", "العنوان الآمن (https) المستخدم لإقران هذا التلفاز", he = "הכתובת המאובטחת (https) שמשמשת לצימוד הטלוויזיה")
    val workerUrlField get() = t("Dashboard URL", "Adresse du tableau de bord", "URL del panel", "عنوان لوحة التحكم", he = "כתובת לוח הבקרה")

    // États
    fun updateTitle(v: String) = when (lang) { AppLang.French -> "Mise à jour disponible · $v"; AppLang.Spanish -> "Actualización disponible · $v"; AppLang.Arabic -> "تحديث متاح · $v"; AppLang.Hebrew -> "עדכון זמין · $v"; else -> "Update available · $v" }
    val updateDefaultBody get() = t("A new version is available. The download is verified before installation.", "Une nouvelle version est disponible. Le téléchargement est vérifié avant installation.", "Hay una versión nueva. La descarga se verifica antes de instalar.", "يتوفر إصدار جديد. يتم التحقق من التنزيل قبل التثبيت.", he = "גרסה חדשה זמינה. ההורדה נבדקת לפני ההתקנה.")
    val updateFailed get() = t("The update could not be installed. Try again later.", "La mise à jour n’a pas pu être installée. Réessayez plus tard.", "No se pudo instalar la actualización. Inténtalo más tarde.", "تعذّر تثبيت التحديث. حاول لاحقًا.", he = "לא ניתן היה להתקין את העדכון. נסו שוב מאוחר יותר.")
    val updateSignatureMismatch get() = t("This version is signed with a new key: Android will refuse it as an update. Uninstall Kanaani once, then install the latest version (Downloader: tinyurl.com/22hhezng). Later updates will install normally.", "Cette version est signée avec une nouvelle clé : Android la refusera en mise à jour. Désinstallez Kanaani une seule fois, puis installez la dernière version (Downloader : tinyurl.com/22hhezng). Les mises à jour suivantes s’installeront normalement.", "Esta versión está firmada con una clave nueva: Android la rechazará como actualización. Desinstala Kanaani una vez e instala la última versión (Downloader: tinyurl.com/22hhezng). Las siguientes actualizaciones se instalarán con normalidad.", "هذا الإصدار موقّع بمفتاح جديد: سيرفضه أندرويد كتحديث. ألغِ تثبيت كنعاني مرة واحدة ثم ثبّت أحدث إصدار (Downloader: tinyurl.com/22hhezng). ستُثبَّت التحديثات اللاحقة بشكل عادي.", he = "הגרסה הזו חתומה במפתח חדש: אנדרואיד יסרב להתקין אותה כעדכון. הסירו את כנעני פעם אחת, ואז התקינו את הגרסה האחרונה (Downloader: tinyurl.com/22hhezng). העדכונים הבאים יותקנו כרגיל.")
    val updateLaunched get() = t("The Android installer has opened. If it reports a conflict, uninstall Kanaani then install the latest version (Downloader: tinyurl.com/22hhezng).", "L’installateur Android s’est ouvert. S’il signale un conflit, désinstallez Kanaani puis installez la dernière version (Downloader : tinyurl.com/22hhezng).", "Se abrió el instalador de Android. Si indica un conflicto, desinstala Kanaani e instala la última versión (Downloader: tinyurl.com/22hhezng).", "فُتح مثبّت أندرويد. إذا أشار إلى تعارض، ألغِ تثبيت كنعاني ثم ثبّت أحدث إصدار (Downloader: tinyurl.com/22hhezng).", he = "מתקין האנדרואיד נפתח. אם מופיעה התנגשות, הסירו את כנעני ואז התקינו את הגרסה האחרונה (Downloader: tinyurl.com/22hhezng).")
    val offlineTitle get() = t("No internet connection", "Pas de connexion internet", "Sin conexión a internet", "لا يوجد اتصال بالإنترنت", he = "אין חיבור לאינטרנט")
    val offlineBody get() = t("Your favorites, the guide already loaded and recordings stay available. Live TV resumes as soon as the network is back.", "Vos favoris, le guide déjà chargé et les enregistrements restent disponibles. Le direct reprendra dès le retour du réseau.", "Tus favoritos, la guía ya cargada y las grabaciones siguen disponibles. El directo se reanudará al volver la red.", "تبقى المفضلة والدليل المحمّل والتسجيلات متاحة. سيعود البث المباشر فور عودة الشبكة.", he = "המועדפים, מדריך השידורים שכבר נטען וההקלטות נשארים זמינים. השידור החי יחזור ברגע שהרשת תחזור.")
    val offlineBanner get() = t("No internet connection — saved content stays available.", "Pas de connexion internet — le contenu enregistré reste disponible.", "Sin conexión — el contenido guardado sigue disponible.", "لا يوجد اتصال — المحتوى المحفوظ متاح.", he = "אין חיבור לאינטרנט — התוכן השמור נשאר זמין.")
    fun sourceErrorBody(name: String) = when (lang) {
        AppLang.French -> "L’adresse de $name ne répond plus. Les fournisseurs changent parfois d’adresse : vérifiez-la auprès du vôtre."
        AppLang.Spanish -> "La dirección de $name ya no responde. Los proveedores a veces cambian de dirección: compruébala con el tuyo."
        AppLang.Arabic -> "عنوان $name لم يعد يستجيب. يغيّر المزوّدون عناوينهم أحيانًا: تحقق منه لدى مزوّدك."
        AppLang.Hebrew -> "הכתובת של $name כבר לא מגיבה. ספקים מחליפים לפעמים כתובת: בדקו מול הספק שלכם."
        else -> "The address of $name no longer responds. Providers sometimes change address: check it with yours."
    }
    val retry get() = t("Retry", "Réessayer", "Reintentar", "إعادة المحاولة", he = "נסו שוב")
    val seeRecordings get() = t("View recordings", "Voir les enregistrements", "Ver grabaciones", "عرض التسجيلات", he = "צפייה בהקלטות")
    val browseLive get() = t("Browse live TV", "Parcourir le direct", "Explorar el directo", "تصفح البث المباشر", he = "עיון בשידור החי")
    val emptyFavTitle get() = t("No favorites yet", "Aucun favori pour l’instant", "Aún no hay favoritos", "لا توجد مفضلات بعد", he = "אין מועדפים עדיין")
    val emptyFavBody get() = t("Long-press OK on a channel, a movie or a series to add it here.", "Appuyez longuement sur OK sur une chaîne, un film ou une série pour l’ajouter ici.", "Mantén pulsado OK en un canal, película o serie para añadirlo aquí.", "اضغط مطولًا على OK على قناة أو فيلم أو مسلسل لإضافته هنا.", he = "לחיצה ארוכה על OK בערוץ, סרט או סדרה כדי להוסיף אותם לכאן.")

    // Panneau du lecteur
    fun forThisChannel(name: String) = when (lang) { AppLang.French -> "Pour cette lecture · $name"; AppLang.Spanish -> "Para esta reproducción · $name"; AppLang.Arabic -> "لهذا التشغيل · $name"; AppLang.Hebrew -> "להפעלה הזו · $name"; else -> "For this playback · $name" }
    val backToAuto get() = t("Back to automatic", "Revenir en automatique", "Volver a automático", "العودة إلى التلقائي", he = "חזרה למצב אוטומטי")
    val statsShort get() = t("Stats", "Stats", "Stats", "إحصاءات", he = "נתונים")
    val vlcHint get() = t("difficult formats", "formats difficiles", "formatos difíciles", "صيغ صعبة", he = "פורמטים קשים")
    val softwareHint get() = t("more compatible", "plus compatible", "más compatible", "أكثر توافقًا", he = "תואם יותר")
    val onVideo get() = t("on video", "sur la vidéo", "sobre el vídeo", "على الفيديو", he = "על הווידאו")
    val statResolution get() = t("Resolution", "Résolution", "Resolución", "الدقة", he = "רזולוציה")
    val statCodec get() = t("Video codec", "Codec vidéo", "Códec de vídeo", "ترميز الفيديو", he = "קודק וידאו")
    val statAudio get() = t("Audio", "Audio", "Audio", "الصوت", he = "שמע")
    val statBitrate get() = t("Bitrate", "Débit", "Tasa de bits", "معدل البت", he = "קצב נתונים")
    val statDropped get() = t("Dropped frames", "Images perdues", "Fotogramas perdidos", "الإطارات المفقودة", he = "פריימים שנפלו")

    val categoriesLabel get() = t("CATEGORIES", "CATÉGORIES", "CATEGORÍAS", "الفئات", he = "קטגוריות")
    val selectedChannel get() = t("SELECTED CHANNEL", "CHAÎNE SÉLECTIONNÉE", "CANAL SELECCIONADO", "القناة المحددة", he = "הערוץ הנבחר")
    val onAirPill get() = t("ON AIR", "EN COURS", "EN CURSO", "قيد البث", he = "בשידור")
    val drawerHint get() = t("OK zap · ▲▼ browse · ◀ categories · ‹ close", "OK zapper · ▲▼ parcourir · ◀ catégories · ‹ fermer", "OK cambiar · ▲▼ explorar · ◀ categorías · ‹ cerrar", "OK للتبديل · ▲▼ تصفح · ◀ الفئات · ‹ إغلاق", he = "OK מעבר · ▲▼ עיון · ◀ קטגוריות · ‹ סגירה")

    // Filtre de langue de la vue
    val langAll get() = t("All", "Toutes", "Todos", "الكل", he = "הכול")
    fun langPill(v: String) = when (lang) { AppLang.French -> "Langues : $v"; AppLang.Spanish -> "Idiomas: $v"; AppLang.Arabic -> "اللغات: $v"; AppLang.Hebrew -> "שפות: $v"; else -> "Languages: $v" }
    val langMulti get() = t("Multilingual", "Multilingue", "Multilingüe", "متعدد اللغات", he = "רב-לשוני")
    val langUndetermined get() = t("Undetermined", "Non déterminé", "Sin determinar", "غير محدد", he = "לא ידוע")
    val langPanelTitle get() = t("Languages", "Langues", "Idiomas", "اللغات", he = "שפות")
    val langPanelHint get() = t("Temporary filter for this screen only. Your content languages in Settings are not changed.", "Filtre temporaire de cet écran uniquement. Vos langues de contenu dans les Réglages ne changent pas.", "Filtro temporal solo para esta pantalla. Tus idiomas en Ajustes no cambian.", "مرشح مؤقت لهذه الشاشة فقط. لا تتغير لغات المحتوى في الإعدادات.", he = "מסנן זמני למסך הזה בלבד. שפות התוכן בהגדרות לא משתנות.")

    val preferredQuality get() = t("Preferred quality", "Qualité préférée", "Calidad preferida", "الجودة المفضلة", he = "איכות מועדפת")
    val preferredQualityHint get() = t("For channels available in several qualities", "Pour les chaînes proposées en plusieurs qualités", "Para canales con varias calidades", "للقنوات المتوفرة بعدة جودات", he = "לערוצים שזמינים בכמה איכויות")
    val otherQualities get() = t("Other qualities", "Autres qualités", "Otras calidades", "جودات أخرى", he = "איכויות נוספות")

    // Commun
    val live get() = t("LIVE", "EN DIRECT", "EN DIRECTO", "مباشر", he = "חי")
    val watch get() = t("Watch", "Regarder", "Ver", "مشاهدة", he = "צפייה")
    val tvGuide get() = t("TV Guide", "Guide TV", "Guía TV", "دليل التلفاز", he = "מדריך שידורים")
    val until get() = t("until %s", "jusqu’à %s", "hasta %s", "حتى %s", he = "עד %s")
    val channelsCount get() = t("%d channels", "%d chaînes", "%d canales", "%d قناة", he = "%d ערוצים")
    val minLeft get() = t("%d min left", "Reste %d min", "Quedan %d min", "متبقي %d د", he = "נותרו %d דק׳")
    val hourMinLeft get() = t("%1\$d h %2\$02d left", "Reste %1\$d h %2\$02d", "Quedan %1\$d h %2\$02d", "متبقي %1\$d س %2\$02d", he = "נותרו %1\$d שע׳ %2\$02d")

    // Accueil
    val featured get() = t("Featured", "À la une", "Destacado", "الأبرز", he = "מומלצים")
    val continueWatching get() = t("Continue watching", "Reprendre la lecture", "Seguir viendo", "متابعة المشاهدة", he = "המשך צפייה")
    val favoriteChannels get() = t("Favorite channels", "Chaînes favorites", "Canales favoritos", "القنوات المفضلة", he = "ערוצים מועדפים")
    val homeEmpty get() = t("Your catalog is loading…", "Votre catalogue se charge…", "Cargando tu catálogo…", "جارٍ تحميل الكتالوج…", he = "הקטלוג שלכם נטען…")
    val homeNoSource get() = t("Add a source to get started", "Ajoutez une source pour commencer", "Añade una fuente para empezar", "أضف مصدرًا للبدء", he = "הוסיפו מקור כדי להתחיל")
    val syncCloud get() = t("Sync from the cloud", "Synchroniser depuis le cloud", "Sincronizar desde la nube", "المزامنة من السحابة", he = "סנכרון מהענן")
    val syncCloudHint get() = t("Shows a short code to enter on the dashboard", "Affiche un code à saisir sur le tableau de bord", "Muestra un código para introducir en el panel", "يعرض رمزًا لإدخاله في لوحة التحكم", he = "מציג קוד קצר להזנה בלוח הבקרה")

    // Première synchronisation (Chargement.dc.html)
    val firstSync get() = t("FIRST SYNC", "PREMIÈRE SYNCHRONISATION", "PRIMERA SINCRONIZACIÓN", "المزامنة الأولى", he = "סנכרון ראשון")
    val preparing get() = t("Preparing your catalog", "Préparation de votre catalogue", "Preparando tu catálogo", "جارٍ تجهيز الكتالوج", he = "מכין את הקטלוג שלכם")
    val preparingBody get() = t("Live channels arrive first. Movies, series and the TV guide continue in the background.", "Les chaînes en direct arrivent en premier. Films, séries et guide TV continuent en arrière-plan.", "Los canales en directo llegan primero. Películas, series y la guía TV siguen en segundo plano.", "تصل القنوات المباشرة أولًا. تستمر الأفلام والمسلسلات والدليل في الخلفية.", he = "ערוצי השידור החי מגיעים ראשונים. סרטים, סדרות ומדריך השידורים ממשיכים ברקע.")
    val etaAbout get() = t("about %s left", "environ %s restantes", "unos %s restantes", "حوالي %s متبقية", he = "נותרו בערך %s")
    val etaLessThanMinute get() = t("under a minute", "moins d’une minute", "menos de un minuto", "أقل من دقيقة", he = "פחות מדקה")
    val etaMinutes get() = t("%d min", "%d min", "%d min", "%d د", he = "%d דק׳")
    val watchLive get() = t("Watch live TV", "Regarder le direct", "Ver el directo", "مشاهدة المباشر", he = "צפייה בשידור חי")
    val availableWhenReady get() = t("Available as soon as the channels are ready", "Disponible dès que les chaînes sont prêtes", "Disponible en cuanto los canales estén listos", "متاح بمجرد جاهزية القنوات", he = "זמין ברגע שהערוצים מוכנים")
    val stepLive get() = t("Live channels", "Chaînes en direct", "Canales en directo", "القنوات المباشرة", he = "ערוצים חיים")
    val stepMovies get() = t("Movies", "Films", "Películas", "الأفلام", he = "סרטים")
    val stepSeries get() = t("Series", "Séries", "Series", "المسلسلات", he = "סדרות")
    val stepGuide get() = t("TV guide", "Guide TV", "Guía TV", "دليل التلفاز", he = "מדריך שידורים")
    val extraGuide get() = t("Additional guide", "Guide complémentaire", "Guía complementaria", "دليل إضافي", he = "מדריך נוסף")
    val extraGuideOff get() = t("Off", "Désactivé", "Desactivada", "إيقاف", he = "כבוי")
    val extraGuideHint get() = t("Fills the channels your source has no programme for", "Complète les chaînes sans programme chez votre source", "Completa los canales sin programa en su fuente", "يكمل القنوات التي لا يوفر مزودك برامجها", he = "ממלא את הערוצים שאין להם תוכניה במקור שלכם")
    val stateWaiting get() = t("Waiting", "En attente", "En espera", "في الانتظار", he = "ממתין")
    val stateDone get() = t("Done", "Terminé", "Listo", "اكتمل", he = "הושלם")
    val stateFailed get() = t("Failed", "Échec", "Error", "فشل", he = "נכשל")
    val countChannels get() = t("%s channels", "%s chaînes", "%s canales", "%s قناة", he = "%s ערוצים")
    val countMovies get() = t("%s movies", "%s films", "%s películas", "%s فيلم", he = "%s סרטים")
    val countSeries get() = t("%s series", "%s séries", "%s series", "%s مسلسل", he = "%s סדרות")
    val countProgrammes get() = t("%s programmes", "%s programmes", "%s programas", "%s برنامج", he = "%s תוכניות")
    val nextOpenInstant get() = t("Next launch will be instant: the catalog is kept on the device and updated in the background.", "La prochaine ouverture sera instantanée : le catalogue est gardé sur l’appareil et mis à jour en arrière-plan.", "La próxima apertura será instantánea: el catálogo se guarda en el dispositivo y se actualiza en segundo plano.", "الفتح التالي فوري: يُحفظ الكتالوج على الجهاز ويُحدَّث في الخلفية.", he = "ההפעלה הבאה תהיה מיידית: הקטלוג נשמר במכשיר ומתעדכן ברקע.")
    val hintOk get() = t("OK", "OK", he = "אישור")
    val hintWatch get() = t("Watch", "Regarder", "Ver", "مشاهدة", he = "צפייה")
    val openSourceSettings get() = t("Open source settings", "Ouvrir les Réglages des sources", "Abrir los ajustes de fuentes", "فتح إعدادات المصادر", he = "פתיחת הגדרות המקור")
    val fixSource get() = t("Fix the source", "Corriger la source", "Corregir la fuente", "تصحيح المصدر", he = "תיקון המקור")
    // Direct
    val directTitle get() = t("Live", "Direct", "Directo", "مباشر", he = "חי")
    val catFavorites get() = t("Favorites", "Favoris", "Favoritos", "المفضلة", he = "מועדפים")
    val catAll get() = t("All", "Tout", "Todo", "الكل", he = "הכול")
    val noChannels get() = t("No channels in this category", "Aucune chaîne dans cette catégorie", "No hay canales en esta categoría", "لا توجد قنوات في هذه الفئة", he = "אין ערוצים בקטגוריה הזו")
    val noFavorites get() = t("No favorites yet — hold OK on a channel to add one", "Pas encore de favoris — maintenez OK sur une chaîne pour en ajouter", "Aún no hay favoritos: mantén OK en un canal para añadir uno", "لا مفضلات بعد — اضغط مطولًا على OK لإضافة قناة", he = "אין מועדפים עדיין — החזיקו OK על ערוץ כדי להוסיף")
    val upNext get() = t("UP NEXT", "À SUIVRE", "A CONTINUACIÓN", "التالي", he = "בהמשך")
    val noProgramInfo get() = t("No programme information", "Aucune information de programme", "Sin información de programa", "لا توجد معلومات عن البرنامج", he = "אין מידע על התוכנית")
    val addFavorite get() = t("Add to favorites", "Ajouter aux favoris", "Añadir a favoritos", "إضافة إلى المفضلة", he = "הוספה למועדפים")
    val removeFavorite get() = t("Remove from favorites", "Retirer des favoris", "Quitar de favoritos", "إزالة من المفضلة", he = "הסרה מהמועדפים")
    val lockChannel get() = t("Lock channel", "Verrouiller la chaîne", "Bloquear canal", "قفل القناة", he = "נעילת ערוץ")
    val unlockChannel get() = t("Unlock channel", "Déverrouiller la chaîne", "Desbloquear canal", "فتح القناة", he = "ביטול נעילת ערוץ")
    val close get() = t("Close", "Fermer", "Cerrar", "إغلاق", he = "סגירה")
    val hourShort get() = t("%d h", "%d h", "%d h", "%d س", he = "%d שע׳")
    val minShort get() = t("%d min", "%d min", "%d min", "%d د", he = "%d דק׳")
    val loading get() = t("Loading…", "Chargement…", "Cargando…", "جارٍ التحميل…", he = "טוען…")
    // Guide
    val today get() = t("Today", "Aujourd’hui", "Hoy", "اليوم", he = "היום")
    val tomorrow get() = t("Tomorrow", "Demain", "Mañana", "غدًا", he = "מחר")
    val announcementTitle get() = t("Notice", "Information", "Aviso", "إشعار", he = "הודעה")
    val announcementOk get() = t("OK", "J’ai compris", "Entendido", "حسنًا", he = "אישור")
    val langSystem get() = t("System (auto)", "Système (auto)", "Sistema (auto)", "النظام (تلقائي)", he = "מערכת (אוטומטי)")
    /** Nom d'une langue dans les choix : chaque langue dans sa propre langue, sauf « Système », traduit. */
    fun langName(l: AppLang) = if (l == AppLang.System) langSystem else l.displayName
    val guideNoDataYet get() = t("No programmes for this day yet: the provider only publishes the next few hours.", "Pas encore de programmes pour ce jour : le fournisseur ne publie que les prochaines heures.", "Aún no hay programas para este día: el proveedor solo publica las próximas horas.", "لا توجد برامج لهذا اليوم بعد: المزوّد ينشر الساعات القادمة فقط.", he = "אין עדיין תוכניות ליום הזה: הספק מפרסם רק את השעות הקרובות.")
    val guideNoData get() = t("The programme guide is still loading. It will appear here as soon as it is ready.", "Le guide des programmes se charge encore. Il apparaîtra ici dès qu’il sera prêt.", "La guía de programas aún se está cargando.", "لا يزال دليل البرامج قيد التحميل.", he = "מדריך השידורים עדיין נטען. הוא יופיע כאן ברגע שיהיה מוכן.")
    val remind get() = t("Remind me", "Me le rappeler", "Recordármelo", "ذكّرني", he = "תזכורת")
    // Films / Séries
    val moviesTitle get() = t("Movies", "Films", "Películas", "الأفلام", he = "סרטים")
    val seriesTitle get() = t("Series", "Séries", "Series", "المسلسلات", he = "סדרות")
    val allChip get() = t("All", "Tous", "Todos", "الكل", he = "הכול")
    val noMovies get() = t("No movies yet — your catalog is still loading.", "Aucun film pour l’instant — votre catalogue se charge encore.", "Aún no hay películas: el catálogo se está cargando.", "لا توجد أفلام بعد — لا يزال الكتالوج قيد التحميل.", he = "אין סרטים עדיין — הקטלוג עדיין נטען.")
    val noSeries get() = t("No series yet — your catalog is still loading.", "Aucune série pour l’instant — votre catalogue se charge encore.", "Aún no hay series: el catálogo se está cargando.", "لا توجد مسلسلات بعد — لا يزال الكتالوج قيد التحميل.", he = "אין סדרות עדיין — הקטלוג עדיין נטען.")
    // Lecteur
    val pTracks get() = t("Tracks", "Pistes", "Pistas", "المسارات", he = "רצועות")
    val pDisplay get() = t("Display", "Affichage", "Pantalla", "العرض", he = "תצוגה")
    val pRecord get() = t("Record", "Enregistrer", "Grabar", "تسجيل", he = "הקלטה")
    val pChannels get() = t("Channels", "Chaînes", "Canales", "القنوات", he = "ערוצים")
    val pPlayer get() = t("Player", "Lecteur", "Reproductor", "المشغل", he = "נגן")
    val errNoResponse get() = t("This channel is not responding", "Cette chaîne ne répond pas", "Este canal no responde", "هذه القناة لا تستجيب", he = "הערוץ הזה לא מגיב")
    val errRefused get() = t("The provider refused the connection", "Le fournisseur a refusé la connexion", "El proveedor rechazó la conexión", "رفض المزود الاتصال", he = "הספק סירב לחיבור")
    val errRefusedHint get() = t("Another device or stream may already be using your single connection.", "Un autre appareil ou flux utilise peut-être déjà votre connexion unique.", "Otro dispositivo o flujo puede estar usando tu única conexión.", "ربما يستخدم جهاز أو بث آخر اتصالك الوحيد.", he = "ייתכן שמכשיר או שידור אחר כבר משתמשים בחיבור היחיד שלכם.")
    val errNetwork get() = t("Network problem", "Problème de réseau", "Problema de red", "مشكلة في الشبكة", he = "בעיית רשת")
    val errFormat get() = t("This format cannot be played", "Ce format ne peut pas être lu", "No se puede reproducir este formato", "لا يمكن تشغيل هذا التنسيق", he = "לא ניתן להפעיל את הפורמט הזה")
    val errNotFound get() = t("This channel no longer exists", "Cette chaîne n’existe plus", "Este canal ya no existe", "لم تعد هذه القناة موجودة", he = "הערוץ הזה כבר לא קיים")
    val nextChannel get() = t("Next channel", "Chaîne suivante", "Canal siguiente", "القناة التالية", he = "הערוץ הבא")
    val noticeVlc get() = t("Playing with VLC", "Lecture avec VLC", "Reproduciendo con VLC", "التشغيل عبر VLC", he = "מנגן עם VLC")
    val noticeExo get() = t("Playing with ExoPlayer", "Lecture avec ExoPlayer", "Reproduciendo con ExoPlayer", "التشغيل عبر ExoPlayer", he = "מנגן עם ExoPlayer")
    val noticeSoftware get() = t("Software decoding", "Décodage logiciel", "Decodificación por software", "فك الترميز البرمجي", he = "פענוח תוכנה")
    val noticeRetry get() = t("Reconnecting…", "Reconnexion…", "Reconectando…", "إعادة الاتصال…", he = "מתחבר מחדש…")
    val engine get() = t("Player engine", "Moteur de lecture", "Motor de reproducción", "محرك التشغيل", he = "מנוע נגן")
    val decoding get() = t("Decoding", "Décodage", "Decodificación", "فك الترميز", he = "פענוח")
    val bufferMemory get() = t("Buffer memory", "Mémoire tampon", "Memoria de búfer", "ذاكرة التخزين المؤقت", he = "זיכרון חוצץ")
    val auto get() = t("Auto", "Auto", "Auto", "تلقائي", he = "אוטומטי")
    val hardware get() = t("Hardware", "Matériel", "Hardware", "عتاد", he = "חומרה")
    val software get() = t("Software", "Logiciel", "Software", "برمجي", he = "תוכנה")
    val bufLow get() = t("Low latency", "Faible latence", "Baja latencia", "زمن انتقال منخفض", he = "השהיה נמוכה")
    val bufBalanced get() = t("Balanced", "Équilibré", "Equilibrado", "متوازن", he = "מאוזן")
    val bufStable get() = t("Stable", "Stable", "Estable", "مستقر", he = "יציב")
    val bufCustom get() = t("Custom", "Personnalisé", "Personalizado", "مخصص", he = "מותאם אישית")
    val aspectFit get() = t("Fit", "Ajusté", "Ajustar", "ملاءمة", he = "התאמה")
    val aspectFill get() = t("Fill", "Rempli", "Rellenar", "تعبئة", he = "מילוי")
    val aspectZoom get() = t("Zoom", "Zoom", "Zoom", "تكبير", he = "זום")
    val speed get() = t("Speed", "Vitesse", "Velocidad", "السرعة", he = "מהירות")
    val sleepTimer get() = t("Sleep timer", "Minuterie", "Temporizador", "مؤقت النوم", he = "טיימר שינה")
    val statsLabel get() = t("Statistics", "Statistiques", "Estadísticas", "الإحصاءات", he = "סטטיסטיקה")
    val audio get() = t("Audio", "Audio", "Audio", "الصوت", he = "שמע")
    val subtitles get() = t("Subtitles", "Sous-titres", "Subtítulos", "الترجمة", he = "כתוביות")
    val off get() = t("Off", "Désactivés", "Desactivados", "إيقاف", he = "כבוי")
    val bufferClamped get() = t("Buffer reduced to fit this device's memory", "Tampon réduit pour tenir dans la mémoire de cet appareil", "Búfer reducido para la memoria de este dispositivo", "تم تقليل المخزن المؤقت ليناسب ذاكرة الجهاز", he = "החוצץ הוקטן כדי להתאים לזיכרון של המכשיר")
    val engineExo get() = t("ExoPlayer", "ExoPlayer", "ExoPlayer", "ExoPlayer", he = "ExoPlayer")
    val engineVlc get() = t("VLC", "VLC", "VLC", "VLC", he = "VLC")
    fun sections(n: Int) = when (lang) { AppLang.French -> if (n == 1) "1 section" else "$n sections"; AppLang.Spanish -> if (n == 1) "1 sección" else "$n secciones"; AppLang.Arabic -> "$n أقسام"; AppLang.Hebrew -> if (n == 1) "מקטע אחד" else "$n מקטעים"; else -> if (n == 1) "1 section" else "$n sections" }
    fun channels(n: Int): String { val f = java.text.NumberFormat.getIntegerInstance().format(n); return when (lang) { AppLang.French -> if (n <= 1) "$f chaîne" else "$f chaînes"; AppLang.Spanish -> if (n == 1) "1 canal" else "$f canales"; AppLang.Arabic -> "$f قناة"; AppLang.Hebrew -> if (n == 1) "ערוץ אחד" else "$f ערוצים"; else -> if (n == 1) "1 channel" else "$f channels" } }
    fun categoryHeader(name: String, n: Int) = "$name · ${channels(n)}"
    // Gérer les catégories
    val settingsTitle get() = t("Settings", "Réglages", "Ajustes", "الإعدادات", he = "הגדרות")
    val manageCategories get() = t("Manage categories", "Gérer les catégories", "Gestionar categorías", "إدارة الفئات", he = "ניהול קטגוריות")
    val filterHint get() = t("Filter: FR, sport, 4K…", "Filtrer : FR, sport, 4K…", "Filtrar: ES, deportes, 4K…", "تصفية: FR، رياضة، 4K…", he = "סינון: ספורט, 4K…")
    val enableAll get() = t("Enable all", "Tout activer", "Activar todo", "تفعيل الكل", he = "הפעלת הכול")
    val disableAll get() = t("Disable all", "Tout désactiver", "Desactivar todo", "تعطيل الكل", he = "השבתת הכול")
    val categoryCol get() = t("CATEGORY", "CATÉGORIE", "CATEGORÍA", "الفئة", he = "קטגוריה")
    val activeCol get() = t("ACTIVE", "ACTIVE", "ACTIVA", "مفعّلة", he = "פעיל")
    val orderCol get() = t("ORDER", "ORDRE", "ORDEN", "الترتيب", he = "סדר")
    val categoriesFooter get() = t("Disabled: the category is no longer downloaded or shown. Faster sync, lighter box.", "Désactivée : la catégorie n’est plus téléchargée ni affichée. Synchro plus rapide, box plus légère.", "Desactivada: la categoría ya no se descarga ni se muestra. Sincronización más rápida.", "معطّلة: لا يتم تنزيل الفئة ولا عرضها. مزامنة أسرع وجهاز أخف.", he = "מושבת: הקטגוריה כבר לא מורדת ולא מוצגת. סנכרון מהיר יותר, ממיר קל יותר.")
    val toggle get() = t("Toggle", "Basculer", "Alternar", "تبديل", he = "החלפה")
    val moveHint get() = t("▲▼ move · OK confirm", "▲▼ déplacer · OK valider", "▲▼ mover · OK confirmar", "▲▼ تحريك · OK تأكيد", he = "▲▼ הזזה · OK אישור")
    val noFilterSupport get() = t("Your source does not allow partial downloads: disabled categories are hidden.", "Votre source ne permet pas le téléchargement partiel : les catégories désactivées sont masquées.", "Tu fuente no permite descargas parciales: las categorías desactivadas se ocultan.", "مصدرك لا يسمح بالتنزيل الجزئي: تُخفى الفئات المعطّلة.", he = "המקור שלכם לא מאפשר הורדות חלקיות: קטגוריות מושבתות מוסתרות.")
    fun channelsOf(n: Int) = channels(n)
    fun moviesOf(n: Int): String { val f = java.text.NumberFormat.getIntegerInstance().format(n); return when (lang) { AppLang.French -> if (n <= 1) "$f film" else "$f films"; AppLang.Spanish -> if (n == 1) "1 película" else "$f películas"; AppLang.Arabic -> "$f فيلم"; AppLang.Hebrew -> if (n == 1) "סרט אחד" else "$f סרטים"; else -> if (n == 1) "1 movie" else "$f movies" } }
    fun seriesOf(n: Int): String { val f = java.text.NumberFormat.getIntegerInstance().format(n); return when (lang) { AppLang.French -> if (n <= 1) "$f série" else "$f séries"; AppLang.Spanish -> if (n == 1) "1 serie" else "$f series"; AppLang.Arabic -> "$f مسلسل"; AppLang.Hebrew -> if (n == 1) "סדרה אחת" else "$f סדרות"; else -> if (n == 1) "1 series" else "$f series" } }
    val lockedTitle get() = t("Locked channel", "Chaîne verrouillée", "Canal bloqueado", "قناة مقفلة", he = "ערוץ נעול")
    val lockedSubtitle get() = t("Enter the parental code to watch %s.", "Saisissez le code parental pour regarder %s.", "Introduce el código parental para ver %s.", "أدخل الرمز الأبوي لمشاهدة %s.", he = "הזינו את קוד ההורים כדי לצפות ב-%s.")
    // Réglages
    val rubSources get() = t("Sources", "Sources", "Fuentes", "المصادر", he = "מקורות")
    val rubSync get() = t("Synchronization", "Synchronisation", "Sincronización", "المزامنة", he = "סנכרון")
    val rubCategories get() = t("Categories", "Catégories", "Categorías", "الفئات", he = "קטגוריות")
    val rubDisplay get() = t("Display", "Affichage", "Pantalla", "العرض", he = "תצוגה")
    val rubPlayback get() = t("Playback", "Lecture", "Reproducción", "التشغيل", he = "הפעלה")
    val rubParental get() = t("Parental control", "Contrôle parental", "Control parental", "الرقابة الأبوية", he = "בקרת הורים")
    val rubLanguages get() = t("Languages", "Langues", "Idiomas", "اللغات", he = "שפות")
    val rubAbout get() = t("About", "À propos", "Acerca de", "حول", he = "אודות")
    val sourcesSubtitle get() = t("Your IPTV subscriptions. The active source feeds live TV, the guide and VOD.", "Vos abonnements IPTV. La source active alimente le direct, le guide et la VOD.", "Tus suscripciones IPTV. La fuente activa alimenta el directo, la guía y el VOD.", "اشتراكاتك. المصدر النشط يغذي المباشر والدليل والفيديو عند الطلب.", he = "המינויים שלכם ל-IPTV. המקור הפעיל מזין את השידור החי, מדריך השידורים ו-VOD.")
    val active get() = t("active", "active", "activa", "نشط", he = "פעיל")
    val edit get() = t("Edit", "Modifier", "Editar", "تعديل", he = "עריכה")
    val addSource get() = t("Add a source", "Ajouter une source", "Añadir una fuente", "إضافة مصدر", he = "הוספת מקור")
    val setDefault get() = t("Set as default", "Définir par défaut", "Usar por defecto", "تعيين كافتراضي", he = "הגדרה כברירת מחדל")
    val syncNow get() = t("Synchronize now", "Synchroniser maintenant", "Sincronizar ahora", "مزامنة الآن", he = "סנכרון עכשיו")
    val deleteSource get() = t("Delete this source", "Supprimer cette source", "Eliminar esta fuente", "حذف هذا المصدر", he = "מחיקת המקור הזה")
    val backup get() = t("Backup", "Sauvegarde", "Copia de seguridad", "نسخة احتياطية", he = "גיבוי")
    val exportBackup get() = t("Export a backup", "Exporter une sauvegarde", "Exportar copia", "تصدير نسخة", he = "ייצוא גיבוי")
    val importBackup get() = t("Restore a backup", "Restaurer une sauvegarde", "Restaurar copia", "استعادة نسخة", he = "שחזור גיבוי")
    val lastUpdate get() = t("Last update %s ago · next at %s", "Dernière mise à jour il y a %s · prochaine à %s", "Última actualización hace %s · próxima a las %s", "آخر تحديث منذ %s · التالي في %s", he = "עדכון אחרון לפני %s · הבא ב-%s")
    val neverSynced get() = t("Never synchronized", "Jamais synchronisé", "Nunca sincronizado", "لم تتم المزامنة بعد", he = "לא סונכרן מעולם")
    val whenToUpdate get() = t("When to update", "Quand mettre à jour", "Cuándo actualizar", "متى يتم التحديث", he = "מתי לעדכן")
    val modeAuto get() = t("Automatic", "Automatique", "Automática", "تلقائي", he = "אוטומטי")
    val modeAutoDesc get() = t("Depends on the box and the connection", "Selon la box et la connexion", "Según el equipo y la conexión", "حسب الجهاز والاتصال", he = "תלוי בממיר ובחיבור")
    val modeLaunch get() = t("At every launch", "À chaque lancement", "En cada inicio", "عند كل تشغيل", he = "בכל הפעלה")
    val modeLaunchDesc get() = t("In the background, without waiting", "En arrière-plan, sans attendre", "En segundo plano, sin esperar", "في الخلفية دون انتظار", he = "ברקע, בלי לחכות")
    val modeScheduled get() = t("Scheduled", "Planifiée", "Programada", "مجدولة", he = "מתוזמן")
    fun modeScheduledDesc(h: Int) = when (lang) { AppLang.French -> "Chaque nuit à %02d:00".format(h); AppLang.Spanish -> "Cada noche a las %02d:00".format(h); AppLang.Arabic -> "كل ليلة %02d:00".format(h); AppLang.Hebrew -> "כל לילה ב-%02d:00".format(h); else -> "Every night at %02d:00".format(h) }
    val modeManual get() = t("Manual", "Manuelle", "Manual", "يدوي", he = "ידני")
    val modeManualDesc get() = t("Only on demand", "Seulement sur demande", "Solo bajo demanda", "عند الطلب فقط", he = "רק לפי בקשה")
    val contentToSync get() = t("Content to synchronize", "Contenus à synchroniser", "Contenido a sincronizar", "المحتوى المراد مزامنته", he = "תוכן לסנכרון")
    val wifiOnly get() = t("Wi-Fi or Ethernet only", "Seulement en Wi-Fi ou Ethernet", "Solo Wi-Fi o Ethernet", "Wi-Fi أو إيثرنت فقط", he = "Wi-Fi או Ethernet בלבד")
    val activeDisabled get() = t("%1\$d active · %2\$d disabled", "%1\$d actives · %2\$d désactivées", "%1\$d activas · %2\$d desactivadas", "%1\$d مفعّلة · %2\$d معطّلة", he = "%1\$d פעילות · %2\$d מושבתות")
    val purgeTitle get() = t("Remove the downloaded data too?", "Supprimer aussi les données téléchargées ?", "¿Eliminar también los datos descargados?", "حذف البيانات المنزّلة أيضًا؟", he = "להסיר גם את הנתונים שהורדו?")
    val keepData get() = t("Keep the data", "Garder les données", "Conservar los datos", "الاحتفاظ بالبيانات", he = "שמירת הנתונים")
    val purgeData get() = t("Remove the data", "Supprimer les données", "Eliminar los datos", "حذف البيانات", he = "הסרת הנתונים")
    val theme get() = t("Theme", "Thème", "Tema", "السمة", he = "ערכת נושא")
    val themeDark get() = t("Dark", "Sombre", "Oscuro", "داكن", he = "כהה")
    val themeLight get() = t("Light", "Clair", "Claro", "فاتح", he = "בהיר")
    val themeAuto get() = t("Automatic", "Automatique", "Automático", "تلقائي", he = "אוטומטי")
    val language get() = t("Language", "Langue", "Idioma", "اللغة", he = "שפה")
    val menu get() = t("Menu", "Menu", "Menú", "القائمة", he = "תפריט")
    val menuSidebar get() = t("Sidebar", "Barre latérale", "Barra lateral", "شريط جانبي", he = "סרגל צד")
    val menuTop get() = t("Top bar", "Barre du haut", "Barra superior", "شريط علوي", he = "סרגל עליון")
    val channelNumbers get() = t("Channel numbers", "Numéros de chaînes", "Números de canal", "أرقام القنوات", he = "מספרי ערוצים")
    val shown get() = t("Shown", "Affichés", "Mostrados", "ظاهرة", he = "מוצג")
    val hidden get() = t("Hidden", "Masqués", "Ocultos", "مخفية", he = "מוסתר")
    val launchAtBoot get() = t("Launch when the box starts", "Lancer au démarrage de la box", "Iniciar al arrancar el equipo", "التشغيل عند إقلاع الجهاز", he = "הפעלה עם הדלקת הממיר")
    val openOn get() = t("Open on", "Ouvrir sur", "Abrir en", "فتح على", he = "פתיחה ב")
    val timeZone get() = t("Time zone", "Fuseau horaire", "Zona horaria", "المنطقة الزمنية", he = "אזור זמן")
    val tzSystem get() = t("Device", "Celui de l’appareil", "El del dispositivo", "الخاص بالجهاز", he = "מכשיר")
    val lastChannel get() = t("Last channel", "Dernière chaîne", "Último canal", "آخر قناة", he = "ערוץ אחרון")
    val homeScreen get() = t("Home", "Accueil", "Inicio", "الرئيسية", he = "בית")
    val logosFolder get() = t("Local channel logos", "Logos de chaînes locaux", "Logotipos locales", "شعارات القنوات المحلية", he = "לוגואים מקומיים לערוצים")
    val chooseFolder get() = t("Choose a folder", "Choisir un dossier", "Elegir carpeta", "اختيار مجلد", he = "בחירת תיקייה")
    val playerRow get() = t("Player", "Lecteur", "Reproductor", "المشغل", he = "נגן")
    val decodingRow get() = t("Decoding", "Décodage", "Decodificación", "فك الترميز", he = "פענוח")
    val bufferRow get() = t("Buffer memory", "Mémoire tampon", "Memoria de búfer", "ذاكرة التخزين المؤقت", he = "זיכרון חוצץ")
    val maxQuality get() = t("Maximum quality", "Qualité maximale", "Calidad máxima", "أعلى جودة", he = "איכות מרבית")
    val externalPlayer get() = t("External player", "Lecteur externe", "Reproductor externo", "مشغل خارجي", he = "נגן חיצוני")
    val none get() = t("None", "Aucun", "Ninguno", "بدون", he = "ללא")
    val autoNext get() = t("Next episode automatically", "Épisode suivant automatique", "Siguiente episodio automático", "الحلقة التالية تلقائيًا", he = "פרק הבא אוטומטית")
    val resumePlayback get() = t("Resume playback", "Reprendre la lecture", "Reanudar reproducción", "استئناف التشغيل", he = "המשך הפעלה")
    val compatVideo get() = t("Compatibility video rendering", "Rendu vidéo de compatibilité", "Renderizado de vídeo compatible", "عرض الفيديو المتوافق", he = "עיבוד וידאו תואם")
    val compatVideoHint get() = t(
        "Turn on if you hear the sound but see no picture. On by default on smartphones, tablets and NVIDIA Shield.",
        "À activer si vous entendez le son sans voir l’image. Activé d’office sur smartphone, tablette et NVIDIA Shield.",
        "Actívalo si oyes el sonido pero no ves la imagen. Activado por defecto en móviles, tabletas y NVIDIA Shield.",
        "فعّله إذا كنت تسمع الصوت دون رؤية الصورة. مفعّل افتراضيًا على الهواتف والأجهزة اللوحية وNVIDIA Shield.", he = "הפעילו אם שומעים צליל אבל לא רואים תמונה. מופעל כברירת מחדל בסמארטפונים, טאבלטים ו-NVIDIA Shield.",
    )
    val autoFps get() = t("Match screen refresh rate", "Fréquence d’écran adaptée à la vidéo", "Adaptar la frecuencia de pantalla", "مطابقة معدل تحديث الشاشة", he = "התאמה לקצב הרענון של המסך")
    val on get() = t("On", "Activé", "Activado", "مفعّل", he = "מופעל")
    val offState get() = t("Off", "Désactivé", "Desactivado", "معطّل", he = "כבוי")
    val appLanguage get() = t("App language", "Langue de l’application", "Idioma de la aplicación", "لغة التطبيق", he = "שפת האפליקציה")
    val contentLanguages get() = t("Content languages", "Langues des contenus", "Idiomas del contenido", "لغات المحتوى", he = "שפות התוכן")
    val langQuestion get() = t("Which languages do you watch?", "Dans quelles langues regardez-vous ?", "¿En qué idiomas ve contenido?", "بأي لغات تشاهد؟", he = "באילו שפות אתם צופים?")
    val langHelp get() = t("We only download the matching categories. You can change this later in Settings.", "Nous ne téléchargeons que les catégories correspondantes. Modifiable plus tard dans les réglages.", "Solo descargamos las categorías correspondientes. Puede cambiarlo más tarde en Ajustes.", "نحمّل الفئات المطابقة فقط. يمكنك التغيير لاحقًا من الإعدادات.", he = "נוריד רק את הקטגוריות המתאימות. אפשר לשנות זאת אחר כך בהגדרות.")
    val continueLabel get() = t("Continue", "Continuer", "Continuar", "متابعة", he = "המשך")
    val langLoading get() = t("Reading categories…", "Lecture des catégories…", "Leyendo categorías…", "جارٍ قراءة الفئات…", he = "קורא קטגוריות…")
    val allLanguages get() = t("All languages", "Toutes les langues", "Todos los idiomas", "كل اللغات", he = "כל השפות")
    val includeMulti get() = t("Include multilingual content", "Inclure les contenus multilingues", "Incluir contenido multilingüe", "تضمين المحتوى متعدد اللغات", he = "כולל תוכן רב-לשוני")
    val includeUnknown get() = t("Include content of unknown language", "Inclure les contenus de langue inconnue", "Incluir contenido de idioma desconocido", "تضمين المحتوى مجهول اللغة", he = "כולל תוכן בשפה לא ידועה")
    val version get() = t("Version", "Version", "Versión", "الإصدار", he = "גרסה")
    val checkUpdates get() = t("Check for updates", "Rechercher une mise à jour", "Buscar actualizaciones", "البحث عن تحديث", he = "בדיקת עדכונים")
    val diagnostic get() = t("Diagnostic", "Diagnostic", "Diagnóstico", "التشخيص", he = "אבחון")
    val diagnosticHint get() = t("What the app measured and tuned for you", "Ce que l’app a mesuré et réglé pour vous", "Lo que la app midió y ajustó", "ما قاسه التطبيق وضبطه لك", he = "מה האפליקציה מדדה וכיוונה עבורכם")
    val telemetry get() = t("Anonymous crash reports", "Rapports de plantage anonymes", "Informes de fallos anónimos", "تقارير الأعطال المجهولة", he = "דיווחי קריסה אנונימיים")
    val cloudSync get() = t("Sync from the cloud", "Synchroniser depuis le cloud", "Sincronizar desde la nube", "المزامنة من السحابة", he = "סנכרון מהענן")
    val cloudSyncHint get() = t("Shows a code to enter on the dashboard", "Affiche un code à saisir sur le tableau de bord", "Muestra un código para el panel", "يعرض رمزًا لإدخاله في لوحة التحكم", he = "מציג קוד להזנה בלוח הבקרה")
    val paired get() = t("Paired", "Appairé", "Emparejado", "مقترن", he = "מצומד")
    val unpair get() = t("Unpair this TV", "Dissocier cette TV", "Desvincular esta TV", "إلغاء اقتران هذا التلفاز", he = "ביטול צימוד הטלוויזיה")
    val liveTv get() = t("Live channels", "Chaînes en direct", "Canales en directo", "القنوات المباشرة", he = "ערוצים חיים")
    fun everyHours(h: Int) = when (lang) { AppLang.French -> "toutes les $h h"; AppLang.Spanish -> "cada $h h"; AppLang.Arabic -> "كل $h س"; AppLang.Hebrew -> "כל $h שע׳"; else -> "every $h h" }
    val everyDay get() = t("every day", "chaque jour", "cada día", "كل يوم", he = "כל יום")
    val everyNight get() = t("every night", "chaque nuit", "cada noche", "كل ليلة", he = "כל לילה")
    fun guideWindow(back: Int, fwd: Int) = when (lang) { AppLang.French -> "Fenêtre de $back h / $fwd h"; AppLang.Spanish -> "Ventana de $back h / $fwd h"; AppLang.Arabic -> "نافذة $back س / $fwd س"; AppLang.Hebrew -> "חלון של $back שע׳ / $fwd שע׳"; else -> "$back h / $fwd h window" }
    val hoursShort get() = t("%d h", "%d h", "%d h", "%d س", he = "%d שע׳")
    val minutesShort get() = t("%d min", "%d min", "%d min", "%d د", he = "%d דק׳")
    val cloudSyncImport get() = t("Import my configuration from the cloud", "Importer ma configuration depuis le cloud", "Importar mi configuración desde la nube", "استيراد إعداداتي من السحابة", he = "ייבוא ההגדרות שלי מהענן")
    val reevaluate get() = t("Re-evaluate", "Réévaluer", "Reevaluar", "إعادة التقييم", he = "הערכה מחדש")
    val device get() = t("Device", "Appareil", "Dispositivo", "الجهاز", he = "מכשיר")
    val connection get() = t("Connection", "Connexion", "Conexión", "الاتصال", he = "חיבור")
    val autoSettings get() = t("Auto settings", "Réglages auto", "Ajustes auto", "إعدادات تلقائية", he = "הגדרות אוטומטיות")
    val tierLow get() = t("Low", "Bas", "Bajo", "منخفض", he = "נמוך"); val tierMid get() = t("Mid", "Moyen", "Medio", "متوسط", he = "בינוני"); val tierHigh get() = t("High", "Haut", "Alto", "مرتفع", he = "גבוה")
    val qPoor get() = t("Poor", "Faible", "Débil", "ضعيف", he = "חלש"); val qFair get() = t("Fair", "Moyenne", "Media", "متوسط", he = "סביר"); val qGood get() = t("Good", "Bonne", "Buena", "جيد", he = "טוב"); val qExcellent get() = t("Excellent", "Excellente", "Excelente", "ممتاز", he = "מצוין")
    val memory get() = t("Memory", "Mémoire", "Memoria", "الذاكرة", he = "זיכרון"); val processor get() = t("Processor", "Processeur", "Procesador", "المعالج", he = "מעבד"); val cores get() = t("cores", "cœurs", "núcleos", "أنوية", he = "ליבות")
    val weak get() = t("low", "faible", "baja", "منخفضة", he = "נמוך"); val comfortable get() = t("comfortable", "confortable", "suficiente", "كافية", he = "נוח")
    val hwDecoders get() = t("Hardware decoders", "Décodeurs matériels", "Decodificadores hardware", "مفككات العتاد", he = "מפענחי חומרה"); val screen get() = t("Screen", "Écran", "Pantalla", "الشاشة", he = "מסך")
    val type get() = t("Type", "Type", "Tipo", "النوع", he = "סוג"); val mobile get() = t("Mobile", "Mobile", "Móvil", "محمول", he = "נייד"); val measuredRate get() = t("Measured rate", "Débit mesuré", "Velocidad medida", "السرعة المقاسة", he = "קצב שנמדד")
    val latency get() = t("Latency", "Latence", "Latencia", "زمن الاستجابة", he = "השהיה"); val cuts get() = t("Cuts (2 min)", "Coupures (2 min)", "Cortes (2 min)", "الانقطاعات (دقيقتان)", he = "ניתוקים (2 דק׳)"); val metered get() = t("Metered", "Facturée", "Medida", "محدودة", he = "לפי שימוש")
    val yes get() = t("Yes", "Oui", "Sí", "نعم", he = "כן"); val no get() = t("No", "Non", "No", "لا", he = "לא"); val activeBadge get() = t("Active", "Actifs", "Activos", "نشطة", he = "פעיל"); val manualOverride get() = t("Manual choices", "Choix manuels", "Elecciones manuales", "اختيارات يدوية", he = "בחירות ידניות")
    val animations get() = t("Animations", "Animations", "Animaciones", "الحركات", he = "אנימציות"); val full get() = t("Full", "Complètes", "Completas", "كاملة", he = "מלא"); val reduced get() = t("Reduced", "Réduites", "Reducidas", "مخفّضة", he = "מופחת"); val guideKept get() = t("Guide kept", "Guide gardé", "Guía conservada", "الدليل المحفوظ", he = "מדריך נשמר")
    val holdToReorder get() = t("Hold: reorder", "Maintenir : ordre", "Mantener: orden", "اضغط مطولًا: ترتيب", he = "החזקה: שינוי סדר")
    val syncing get() = t("Syncing", "Synchronisation", "Sincronizando", "جارٍ المزامنة", he = "מסנכרן")
}

val LocalDs = compositionLocalOf { DesignStrings(AppLang.English) }

@Composable
fun designStringsFor(lang: AppLang): DesignStrings {
    val resolved = if (lang == AppLang.System) {
        val sys = LocalConfiguration.current.locales.get(0)?.language ?: "en"
        AppLang.entries.firstOrNull { it.code == sys } ?: AppLang.English
    } else lang
    return remember(resolved) { DesignStrings(resolved) }
}
