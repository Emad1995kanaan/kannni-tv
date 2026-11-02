package com.kanaani.ibo.ui

import com.kanaani.ibo.data.Store

/** Langues proposées : arabe (par défaut), anglais, hébreu. */
enum class Lang(val code: String, val label: String, val rtl: Boolean) {
    AR("ar", "العربية", true),
    EN("en", "English", false),
    HE("he", "עברית", true);

    companion object {
        fun of(code: String) = entries.firstOrNull { it.code == code } ?: AR
    }
}

/** Textes de l'interface. Lus pendant la composition : un changement de langue recompose tout. */
object Str {
    val lang: Lang get() = Lang.of(Store.language)
    private fun t(en: String, ar: String, he: String) = when (lang) { Lang.AR -> ar; Lang.HE -> he; Lang.EN -> en }

    // Accueil
    val live get() = t("Live TV", "البث المباشر", "שידור חי")
    val movies get() = t("Movies", "الأفلام", "סרטים")
    val series get() = t("Series", "المسلسلات", "סדרות")
    val favorites get() = t("Favorites", "المفضلة", "מועדפים")
    val account get() = t("Account", "الحساب", "חשבון")
    val settings get() = t("Settings", "الإعدادات", "הגדרות")
    val welcome get() = t("Welcome", "أهلاً وسهلاً", "ברוכים הבאים")
    val expires get() = t("Expires", "ينتهي في", "תוקף עד")
    val daysLeft get() = t("days left", "يوم متبقٍ", "ימים נותרו")
    val unlimited get() = t("Unlimited", "غير محدود", "ללא הגבלה")

    // Connexion
    val signIn get() = t("Sign in", "تسجيل الدخول", "התחברות")
    val signInHint get() = t("Enter the username and password of your subscription", "أدخل اسم المستخدم وكلمة المرور الخاصة باشتراكك", "הזינו את שם המשתמש והסיסמה של המנוי שלכם")
    val username get() = t("Username", "اسم المستخدم", "שם משתמש")
    val password get() = t("Password", "كلمة المرور", "סיסמה")
    val login get() = t("Login", "دخول", "כניסה")
    val checking get() = t("Checking…", "جارٍ التحقق…", "בודק…")
    val tagline get() = t("Live TV · Movies · Series", "بث مباشر · أفلام · مسلسلات", "שידור חי · סרטים · סדרות")
    val errWrong get() = t("Wrong username or password.", "اسم المستخدم أو كلمة المرور غير صحيحة.", "שם משתמש או סיסמה שגויים.")
    val errNetwork get() = t("Cannot reach the server. Check your internet connection.", "تعذّر الاتصال بالسيرفر. تأكد من اتصال الإنترنت.", "אין גישה לשרת. בדקו את חיבור האינטרנט.")
    val errExpired get() = t("Your subscription has expired. Contact your reseller to renew it.", "انتهى اشتراكك. تواصل مع الموزّع لتجديده.", "המנוי שלכם הסתיים. פנו למשווק כדי לחדש אותו.")
    val errDisabled get() = t("Your subscription is suspended. Contact your reseller.", "اشتراكك موقوف. تواصل مع الموزّع.", "המנוי שלכם מושהה. פנו למשווק.")

    // Listes
    val all get() = t("All", "الكل", "הכול")
    val categories get() = t("Categories", "الفئات", "קטגוריות")
    val search get() = t("Search…", "بحث…", "חיפוש…")
    val loading get() = t("Loading…", "جارٍ التحميل…", "טוען…")
    val empty get() = t("Nothing here", "لا يوجد محتوى", "אין כאן תוכן")
    val loadFailed get() = t("Could not load. Press to retry.", "تعذّر التحميل. اضغط لإعادة المحاولة.", "הטעינה נכשלה. לחצו לנסות שוב.")
    val channels get() = t("channels", "قناة", "ערוצים")
    val previewHint get() = t("Press OK again for full screen", "اضغط مرة ثانية لملء الشاشة", "לחצו שוב למסך מלא")
    val chooseChannel get() = t("Choose a channel to watch", "اختر قناة للمشاهدة", "בחרו ערוץ לצפייה")

    // Fiches
    val play get() = t("Play", "تشغيل", "הפעלה")
    val addFav get() = t("Add to favorites", "أضف للمفضلة", "הוספה למועדפים")
    val removeFav get() = t("Remove from favorites", "إزالة من المفضلة", "הסרה מהמועדפים")
    val season get() = t("Season", "الموسم", "עונה")
    val episode get() = t("Episode", "الحلقة", "פרק")
    val director get() = t("Director", "إخراج", "במאי")
    val cast get() = t("Cast", "بطولة", "שחקנים")
    val noEpisodes get() = t("No episodes available", "لا توجد حلقات", "אין פרקים זמינים")

    // Lecteur
    val playError get() = t("This stream cannot be played right now.", "تعذّر تشغيل البث حالياً.", "לא ניתן להפעיל את השידור כרגע.")
    val retry get() = t("Retry", "إعادة المحاولة", "נסו שוב")

    // Compte
    val status get() = t("Status", "الحالة", "סטטוס")
    val active get() = t("Active", "فعّال", "פעיל")
    val connections get() = t("Connections", "الاتصالات", "חיבורים")
    val created get() = t("Created", "تاريخ الإنشاء", "נוצר")
    val trial get() = t("Trial", "تجريبي", "ניסיון")
    val yes get() = t("Yes", "نعم", "כן")
    val no get() = t("No", "لا", "לא")

    // Réglages
    val language get() = t("Language", "اللغة", "שפה")
    val refresh get() = t("Refresh content", "تحديث المحتوى", "רענון תוכן")
    val refreshed get() = t("Content will be reloaded", "رح يتم تحديث المحتوى", "התוכן ייטען מחדש")
    val logout get() = t("Log out", "تسجيل الخروج", "התנתקות")
    val logoutAsk get() = t("Log out of this device?", "تسجيل الخروج من هذا الجهاز؟", "להתנתק מהמכשיר הזה?")
    val about get() = t("About", "حول التطبيق", "אודות")
    val version get() = t("Version", "الإصدار", "גרסה")
    val cancel get() = t("Cancel", "إلغاء", "ביטול")
    val ok get() = t("OK", "موافق", "אישור")
}
