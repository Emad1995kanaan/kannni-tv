package com.ultratv.tv.nativeapp.ui.settings

import com.ultratv.tv.nativeapp.i18n.AppLang

/** Libellés de la synchro cloud multi-appareils (FR · EN · ES · AR), dans un fichier dédié. */
class CloudSyncStrings(private val lang: AppLang) {
    private fun t(en: String, fr: String, es: String, ar: String, he: String = en) = when (lang) { AppLang.French -> fr; AppLang.Spanish -> es; AppLang.Arabic -> ar; AppLang.Hebrew -> he; else -> en }

    val group get() = t("Cloud account", "Compte cloud", "Cuenta en la nube", "حساب السحابة", he = "חשבון ענן")
    val syncedWithCloud get() = t("Synchronized with the cloud", "Synchronisé avec le cloud", "Sincronizado con la nube", "متزامن مع السحابة", he = "מסונכרן עם הענן")
    val notSynced get() = t("Not synchronized yet", "Pas encore synchronisé", "Aún no sincronizado", "لم تتم المزامنة بعد", he = "עדיין לא סונכרן")
    val syncFailed get() = t("Cloud unreachable, retrying later", "Cloud injoignable, nouvelle tentative plus tard", "Nube inaccesible, se reintentará", "السحابة غير متاحة، ستتم إعادة المحاولة", he = "הענן לא זמין, ננסה שוב מאוחר יותר")
    fun devicesCount(n: Int) = t("$n device(s)", "$n appareil(s)", "$n dispositivo(s)", "$n جهاز", he = "$n מכשירים")
    fun ago(minutes: Long): String = when {
        minutes < 1 -> t("just now", "à l'instant", "ahora mismo", "الآن", he = "עכשיו")
        minutes < 60 -> t("$minutes min ago", "il y a $minutes min", "hace $minutes min", "منذ $minutes دقيقة", he = "לפני $minutes דק׳")
        minutes < 1440 -> t("${minutes / 60} h ago", "il y a ${minutes / 60} h", "hace ${minutes / 60} h", "منذ ${minutes / 60} ساعة", he = "לפני ${minutes / 60} שע׳")
        else -> t("${minutes / 1440} d ago", "il y a ${minutes / 1440} j", "hace ${minutes / 1440} d", "منذ ${minutes / 1440} يوم", he = "לפני ${minutes / 1440} ימים")
    }
    val syncNow get() = t("Sync now", "Synchroniser maintenant", "Sincronizar ahora", "مزامنة الآن", he = "סנכרון עכשיו")
    val thisDevice get() = t("This device's name", "Nom de cet appareil", "Nombre de este dispositivo", "اسم هذا الجهاز", he = "שם המכשיר הזה")
    val deviceNameHint get() = t("Shown in the dashboard and in sharing lists", "Affiché dans le tableau de bord et dans les listes de partage", "Se muestra en el panel y en las listas", "يظهر في لوحة التحكم وقوائم المشاركة", he = "מוצג בלוח הבקרה וברשימות השיתוף")
    val rename get() = t("Rename", "Renommer", "Renombrar", "إعادة التسمية", he = "שינוי שם")
    val accountDevices get() = t("Devices on this account", "Appareils du compte", "Dispositivos de la cuenta", "أجهزة الحساب", he = "מכשירים בחשבון הזה")
    val manageOnDashboard get() = t("Manage on the dashboard", "Gérer sur le tableau de bord", "Gestionar en el panel", "الإدارة في لوحة التحكم", he = "ניהול בלוח הבקרה")

    val localPrivate get() = t("Local · private to this device", "Locale · privée à cet appareil", "Local · privada de este dispositivo", "محلي · خاص بهذا الجهاز", he = "מקומי · פרטי למכשיר הזה")
    fun cloudShared(n: Int) = t("Cloud · shared with $n device(s)", "Cloud · partagée avec $n appareil(s)", "Nube · compartida con $n dispositivo(s)", "سحابة · مشتركة مع $n جهاز", he = "ענן · משותף עם $n מכשירים")
    val shareSource get() = t("Share this source", "Partager cette source", "Compartir esta fuente", "مشاركة هذا المصدر", he = "שיתוף המקור הזה")
    val editSharing get() = t("Edit sharing", "Modifier le partage", "Editar el uso compartido", "تعديل المشاركة", he = "עריכת שיתוף")
    val stopSharing get() = t("Stop sharing (keep locally)", "Ne plus partager (garder en local)", "Dejar de compartir (mantener local)", "إيقاف المشاركة (إبقاؤه محليًا)", he = "הפסקת שיתוף (שמירה מקומית)")
    val shareTitle get() = t("Share with…", "Partager avec…", "Compartir con…", "مشاركة مع…", he = "שיתוף עם…")
    val allDevices get() = t("All devices", "Tous les appareils", "Todos los dispositivos", "كل الأجهزة", he = "כל המכשירים")
    val thisOne get() = t("this device", "cet appareil", "este dispositivo", "هذا الجهاز", he = "המכשיר הזה")
    val syncDisplayPrefs get() = t("Sync categories and languages", "Synchroniser catégories et langues", "Sincronizar categorías e idiomas", "مزامنة الفئات واللغات", he = "סנכרון קטגוריות ושפות")
    val syncDisplayPrefsHint get() = t("Categories you hide and the languages you choose apply on all your devices", "Les catégories masquées et les langues choisies s’appliquent sur tous vos appareils", "Las categorías ocultas y los idiomas elegidos se aplican en todos tus dispositivos", "تُطبَّق الفئات المخفية واللغات المختارة على جميع أجهزتك", he = "הקטגוריות שאתם מסתירים והשפות שאתם בוחרים חלות על כל המכשירים שלכם")
    val confirm get() = t("Share", "Partager", "Compartir", "مشاركة", he = "שיתוף")
    val offerTitle get() = t("Share this source with your other devices?", "Partager cette source avec vos autres appareils ?", "¿Compartir esta fuente con tus otros dispositivos?", "مشاركة هذا المصدر مع أجهزتك الأخرى؟", he = "לשתף את המקור הזה עם המכשירים האחרים שלכם?")
    val offerBody get() = t("It stays on this device only unless you choose.", "Elle reste sur cet appareil tant que vous ne choisissez pas.", "Se queda solo en este dispositivo salvo que elijas.", "تبقى على هذا الجهاز ما لم تختر غير ذلك.", he = "הוא נשאר רק במכשיר הזה אלא אם תבחרו אחרת.")
    val notNow get() = t("Not now", "Pas maintenant", "Ahora no", "ليس الآن", he = "לא עכשיו")
    val pairFirst get() = t("Pair this device with the cloud first", "Appairez d'abord cet appareil au cloud", "Empareja primero este dispositivo con la nube", "اقرن هذا الجهاز بالسحابة أولاً", he = "צמדו קודם את המכשיר לענן")

    val connectionWarning get() = t(
        "Your subscription only allows one stream at a time: watching on two devices at once will cut one of them.",
        "Votre abonnement n'autorise qu'un flux à la fois : regarder sur deux appareils en même temps coupera l'un des deux.",
        "Tu suscripción solo permite una transmisión a la vez: ver en dos dispositivos a la vez cortará una de ellas.",
        "اشتراكك يسمح ببث واحد فقط في كل مرة: المشاهدة على جهازين معًا ستقطع أحدهما.", he = "המנוי שלכם מאפשר רק שידור אחד בכל פעם: צפייה בשני מכשירים בו-זמנית תנתק אחד מהם.",
    )

    val removalTitle get() = t("Remove sources from this device?", "Retirer des sources de cet appareil ?", "¿Quitar fuentes de este dispositivo?", "إزالة مصادر من هذا الجهاز؟", he = "להסיר מקורות מהמכשיר הזה?")
    fun removalBody(name: String, n: Int) = t(
        "\"$name\" is no longer shared with this device. $n favorite(s) or recording(s) depend on it.",
        "« $name » n'est plus partagée avec cet appareil. $n favori(s) ou enregistrement(s) en dépendent.",
        "«$name» ya no se comparte con este dispositivo. $n favorito(s) o grabación(es) dependen de ella.",
        "لم يعد «$name» مشتركًا مع هذا الجهاز. يعتمد عليه $n من المفضلة أو التسجيلات.", he = "\"$name\" כבר לא משותף עם המכשיר הזה. $n מועדפים או הקלטות תלויים בו.",
    )
    val removeAnyway get() = t("Remove", "Retirer", "Quitar", "إزالة", he = "הסרה")
    val keepLocal get() = t("Keep locally", "Garder en local", "Mantener local", "إبقاء محليًا", he = "שמירה מקומית")
}
