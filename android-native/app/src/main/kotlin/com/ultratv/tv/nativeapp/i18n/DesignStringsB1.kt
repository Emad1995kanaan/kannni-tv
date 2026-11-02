package com.ultratv.tv.nativeapp.i18n

/**
 * Libellés du lot B1 (actions sur un programme, enregistrements programmés, fiches enrichies).
 * Fichier à part pour ne pas toucher à [DesignStrings] (évite les conflits entre branches).
 */
private fun DesignStrings.t(en: String, fr: String, es: String = en, ar: String = en, he: String = en): String = when (lang) {
    AppLang.French -> fr
    AppLang.Spanish -> es
    AppLang.Arabic -> ar
    AppLang.Hebrew -> he
    else -> en
}

// Dialogue « programme » (GuideActions)
val DesignStrings.actReplay get() = t("Watch from the start", "Revoir depuis le début", "Ver desde el principio", "المشاهدة من البداية", he = "צפייה מההתחלה")
val DesignStrings.actReplayHint get() = t("Source replay", "Replay de la source", "Repetición de la fuente", "إعادة من المصدر", he = "שידור חוזר מהמקור")
val DesignStrings.actReplayNone get() = t("Replay not available for this programme", "Pas de replay pour ce programme", "Sin repetición para este programa", "لا توجد إعادة لهذا البرنامج", he = "שידור חוזר לא זמין לתוכנית הזו")
val DesignStrings.actWatchChannel get() = t("Watch the channel", "Regarder la chaîne", "Ver el canal", "مشاهدة القناة", he = "צפייה בערוץ")
val DesignStrings.actWatchChannelHint get() = t("Live now", "En direct maintenant", "En directo ahora", "مباشر الآن", he = "בשידור חי עכשיו")
val DesignStrings.actRemind get() = t("Remind me", "Me rappeler", "Recordármelo", "ذكّرني", he = "תזכורת")
val DesignStrings.actRemindHint get() = t("One minute before it starts", "Une minute avant le début", "Un minuto antes de empezar", "قبل البدء بدقيقة", he = "דקה לפני ההתחלה")
val DesignStrings.actRecord get() = t("Record", "Enregistrer", "Grabar", "تسجيل", he = "הקלטה")
val DesignStrings.actRecordHint get() = t("This programme or the whole series", "Programme ou série entière", "Programa o serie completa", "البرنامج أو المسلسل كاملًا", he = "התוכנית הזו או כל הסדרה")
val DesignStrings.actRecordThis get() = t("This programme only", "Ce programme seulement", "Solo este programa", "هذا البرنامج فقط", he = "רק התוכנית הזו")
val DesignStrings.actRecordSeries get() = t("Whole series", "Série entière", "Serie completa", "المسلسل كاملًا", he = "כל הסדרה")
val DesignStrings.actRecordSeriesHint get() = t("Every broadcast with the same title on this channel", "Chaque diffusion du même titre sur cette chaîne", "Cada emisión con el mismo título en este canal", "كل بث بنفس العنوان على هذه القناة", he = "כל שידור עם אותו שם בערוץ הזה")
val DesignStrings.yesterday get() = t("Yesterday", "Hier", "Ayer", "أمس", he = "אתמול")
val DesignStrings.replayTag get() = t("Replay", "Replay", "Repetición", "إعادة", he = "שידור חוזר")
fun DesignStrings.replayBadge(days: Int) = t("REPLAY $days D", "REPLAY $days J", "REPETICIÓN $days D", "إعادة $days أيام", he = "שידור חוזר $days ימים")

// Rappels et enregistrements : retours à l'utilisateur
val DesignStrings.remindSet get() = t("Reminder set", "Rappel programmé", "Recordatorio programado", "تم ضبط التذكير", he = "התזכורת נקבעה")
val DesignStrings.startsInOneMin get() = t("Starts in 1 min", "Commence dans 1 min", "Empieza en 1 min", "يبدأ بعد دقيقة", he = "מתחיל בעוד דקה")
val DesignStrings.watchWord get() = t("Watch", "Regarder", "Ver", "مشاهدة", he = "צפייה")
fun DesignStrings.recScheduled(n: Int) = when (lang) {
    AppLang.French -> if (n <= 1) "Enregistrement programmé" else "$n enregistrements programmés"
    AppLang.Spanish -> if (n <= 1) "Grabación programada" else "$n grabaciones programadas"
    AppLang.Arabic -> if (n <= 1) "تمت جدولة التسجيل" else "تمت جدولة $n تسجيلات"
    AppLang.Hebrew -> if (n <= 1) "ההקלטה תוזמנה" else "$n הקלטות תוזמנו"
    else -> if (n <= 1) "Recording scheduled" else "$n recordings scheduled"
}
val DesignStrings.recNoSpace get() = t("Not enough free space to record this", "Pas assez d’espace libre pour enregistrer", "No hay espacio suficiente para grabar", "لا توجد مساحة كافية للتسجيل", he = "אין מספיק מקום פנוי להקלטה")
val DesignStrings.recNothing get() = t("This programme is already scheduled or over", "Ce programme est déjà programmé ou terminé", "Este programa ya está programado o terminó", "هذا البرنامج مجدول بالفعل أو انتهى", he = "התוכנית הזו כבר מתוזמנת או שהסתיימה")
val DesignStrings.recScheduledBadge get() = t("SCHED.", "PROG.", "PROG.", "مجدول", he = "מתוזמן")
val DesignStrings.recConnectionBusy get() = t("A recording is in progress on your only connection", "Un enregistrement est en cours sur votre seule connexion", "Hay una grabación en curso en su única conexión", "هناك تسجيل جارٍ على اتصالك الوحيد", he = "הקלטה פועלת על החיבור היחיד שלכם")

// Fiches enrichies TMDB
val DesignStrings.trailerLabel get() = t("Trailer", "Bande-annonce", "Tráiler", "الإعلان", he = "טריילר")
val DesignStrings.tmdbAttribution get() = t(
    "This product uses the TMDB API but is not endorsed or certified by TMDB.",
    "Ce produit utilise l’API TMDB mais n’est ni approuvé ni certifié par TMDB.",
    "Este producto utiliza la API de TMDB pero no está avalado ni certificado por TMDB.",
    "يستخدم هذا المنتج واجهة TMDB لكنه غير معتمد أو مصدّق من TMDB.", he = "מוצר זה משתמש ב-API של TMDB אך אינו מאושר או מוסמך על ידי TMDB.",
)

/** Locale de FORMATAGE (noms de jours, de langues, dates) alignée sur la langue de l'application, pas sur celle de l'appareil. Chiffres latins en arabe. */
val DesignStrings.locale: java.util.Locale get() = when (lang) {
    AppLang.French -> java.util.Locale.FRENCH
    AppLang.Spanish -> java.util.Locale("es")
    AppLang.Arabic -> java.util.Locale.forLanguageTag("ar-u-nu-latn")
    AppLang.Hebrew -> java.util.Locale.forLanguageTag("he")
    else -> java.util.Locale.ENGLISH
}

// Trakt : rangées de l'accueil (seulement ce qui est disponible dans la playlist) et marque « vu »
val DesignStrings.traktWatchlist get() = t("My Trakt watchlist", "Ma watchlist Trakt", "Mi lista de seguimiento de Trakt", "قائمة المشاهدة على Trakt", he = "רשימת הצפייה שלי ב-Trakt")
val DesignStrings.traktRecommended get() = t("Recommended for you (Trakt)", "Recommandé pour vous (Trakt)", "Recomendado para ti (Trakt)", "موصى به لك (Trakt)", he = "מומלץ עבורכם (Trakt)")
val DesignStrings.traktTrending get() = t("Trending (Trakt)", "Tendances (Trakt)", "Tendencias (Trakt)", "الأكثر رواجاً (Trakt)", he = "פופולרי עכשיו (Trakt)")
val DesignStrings.traktPopular get() = t("Popular (Trakt)", "Populaires (Trakt)", "Populares (Trakt)", "الأكثر شعبية (Trakt)", he = "פופולרי (Trakt)")
val DesignStrings.traktWatched get() = t("Watched", "Vu", "Visto", "تمت المشاهدة", he = "נצפה")

// Portail Stalker / Ministra (source par adresse MAC)
val DesignStrings.kindStalker get() = t("Stalker portal (MAC)", "Portail Stalker (MAC)", "Portal Stalker (MAC)", "بوابة Stalker (MAC)", he = "פורטל Stalker (MAC)")
val DesignStrings.formSubStalker get() = t("Portal address and the MAC address registered by your operator", "Adresse du portail et adresse MAC enregistrée chez votre opérateur", "Dirección del portal y dirección MAC registrada por tu operador", "عنوان البوابة وعنوان MAC المسجّل لدى مشغّلك", he = "כתובת הפורטל וכתובת ה-MAC שנרשמה אצל הספק")
val DesignStrings.fieldPortalUrl get() = t("Portal address", "Adresse du portail", "Dirección del portal", "عنوان البوابة", he = "כתובת הפורטל")
val DesignStrings.fieldMac get() = t("MAC address", "Adresse MAC", "Dirección MAC", "عنوان MAC", he = "כתובת MAC")
val DesignStrings.macHint get() = t("Type only digits and letters A to F: the colons are added for you", "Tapez seulement les chiffres et les lettres A à F : les deux-points s’ajoutent seuls", "Escribe solo cifras y letras de la A a la F: los dos puntos se añaden solos", "اكتب الأرقام والأحرف من A إلى F فقط: تُضاف النقطتان تلقائيًا", he = "הקלידו רק ספרות ואותיות A עד F: הנקודתיים מתווספות אוטומטית")
val DesignStrings.fieldPortalLogin get() = t("Portal login (optional)", "Identifiant du portail (facultatif)", "Usuario del portal (opcional)", "اسم مستخدم البوابة (اختياري)", he = "שם משתמש לפורטל (לא חובה)")
val DesignStrings.fieldPortalPassword get() = t("Portal password (optional)", "Mot de passe du portail (facultatif)", "Contraseña del portal (opcional)", "كلمة مرور البوابة (اختياري)", he = "סיסמת פורטל (לא חובה)")
val DesignStrings.stalkerChecking get() = t("Connecting to the portal…", "Connexion au portail…", "Conectando con el portal…", "جارٍ الاتصال بالبوابة…", he = "מתחבר לפורטל…")
val DesignStrings.stalkerFailed get() = t("The portal refused the connection. Check the address and the MAC address.", "Le portail a refusé la connexion. Vérifiez l’adresse et l’adresse MAC.", "El portal rechazó la conexión. Comprueba la dirección y la dirección MAC.", "رفضت البوابة الاتصال. تحقّق من العنوان وعنوان MAC.", he = "הפורטל סירב לחיבור. בדקו את הכתובת ואת כתובת ה-MAC.")
