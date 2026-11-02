package com.ultratv.tv.nativeapp.ui.downloads

import com.ultratv.tv.nativeapp.data.downloads.DownloadError
import com.ultratv.tv.nativeapp.i18n.AppLang
import com.ultratv.tv.nativeapp.i18n.Strings

/**
 * Libellés des téléchargements hors ligne, à part de [com.ultratv.tv.nativeapp.i18n.DesignStrings] pour ne pas se
 * marcher dessus avec les autres chantiers. Vérifiés par TranslationsCompleteTest (en / fr / es / ar).
 */
class DownloadStrings(val lang: AppLang) {
    private fun t(en: String, fr: String, es: String, ar: String, he: String = en): String = when (lang) {
        AppLang.French -> fr
        AppLang.Spanish -> es
        AppLang.Arabic -> ar
        AppLang.Hebrew -> he
        else -> en
    }

    val navTitle get() = t("Downloads", "Téléchargements", "Descargas", "التنزيلات", he = "הורדות")
    val download get() = t("Download", "Télécharger", "Descargar", "تنزيل", he = "הורדה")
    val downloadSeason get() = t("Download season", "Télécharger la saison", "Descargar temporada", "تنزيل الموسم", he = "הורדת העונה")
    val downloaded get() = t("Downloaded", "Téléchargé", "Descargado", "تم التنزيل", he = "הורד")
    val queued get() = t("Waiting", "En attente", "En espera", "في الانتظار", he = "ממתין")
    val paused get() = t("Paused", "En pause", "En pausa", "متوقف مؤقتًا", he = "מושהה")
    val failed get() = t("Failed", "Échec", "Error", "فشل", he = "נכשל")
    val pause get() = t("Pause", "Pause", "Pausar", "إيقاف مؤقت", he = "השהיה")
    val resume get() = t("Resume", "Reprendre", "Reanudar", "استئناف", he = "המשך")
    val retry get() = t("Retry", "Réessayer", "Reintentar", "إعادة المحاولة", he = "נסו שוב")
    val cancel get() = t("Cancel", "Annuler", "Cancelar", "إلغاء", he = "ביטול")
    val delete get() = t("Delete", "Supprimer", "Eliminar", "حذف", he = "מחיקה")
    val play get() = t("Play", "Lire", "Reproducir", "تشغيل", he = "הפעלה")
    val sectionActive get() = t("In progress", "En cours", "En curso", "قيد التنزيل", he = "בתהליך")
    val sectionDone get() = t("Available offline", "Disponibles hors ligne", "Disponibles sin conexión", "متاحة دون اتصال", he = "זמין ללא חיבור")
    val empty get() = t(
        "No downloads yet. Use “Download” on a movie or an episode to watch it without a connection.",
        "Aucun téléchargement. Utilisez « Télécharger » sur un film ou un épisode pour le regarder sans connexion.",
        "Ninguna descarga. Usa «Descargar» en una película o un episodio para verlo sin conexión.",
        "لا توجد تنزيلات. استخدم «تنزيل» على فيلم أو حلقة لمشاهدته دون اتصال.", he = "אין הורדות עדיין. השתמשו ב„הורדה” בסרט או בפרק כדי לצפות בו בלי חיבור.",
    )
    val storageUsed get() = t("Used by downloads", "Utilisé par les téléchargements", "Usado por las descargas", "مستخدم للتنزيلات", he = "בשימוש ההורדות")
    fun storageFree(used: String, free: String) = t("$used · $free free", "$used · $free libres", "$used · $free libres", "$used · $free متاحة", he = "$used · $free פנוי")

    // Retours à l'utilisateur
    val queuedToast get() = t("Download added", "Téléchargement ajouté", "Descarga añadida", "تمت إضافة التنزيل", he = "ההורדה נוספה")
    fun seasonQueued(n: Int) = t("$n episodes added to downloads", "$n épisodes ajoutés aux téléchargements", "$n episodios añadidos a las descargas", "تمت إضافة $n حلقات إلى التنزيلات", he = "$n פרקים נוספו להורדות")
    val alreadyQueued get() = t("Already in your downloads", "Déjà dans vos téléchargements", "Ya está en tus descargas", "موجود بالفعل في تنزيلاتك", he = "כבר נמצא בהורדות שלכם")
    val notDownloadable get() = t("Format not downloadable (streaming only)", "Format non téléchargeable (lecture en ligne seulement)", "Formato no descargable (solo en línea)", "صيغة غير قابلة للتنزيل (مشاهدة مباشرة فقط)", he = "הפורמט לא ניתן להורדה (סטרימינג בלבד)")
    val noSpace get() = t("Not enough free space for this download", "Pas assez d’espace libre pour ce téléchargement", "No hay espacio libre suficiente para esta descarga", "لا توجد مساحة كافية لهذا التنزيل", he = "אין מספיק מקום פנוי להורדה הזו")
    val sourceMissing get() = t("This title is no longer in the source catalogue", "Ce titre n’est plus dans le catalogue de la source", "Este título ya no está en el catálogo de la fuente", "لم يعد هذا العنوان في كتالوج المصدر", he = "הכותר הזה כבר לא נמצא בקטלוג המקור")
    val networkError get() = t("Connection lost — it will resume automatically", "Connexion perdue — reprise automatique", "Conexión perdida: se reanudará automáticamente", "انقطع الاتصال — سيُستأنف تلقائيًا", he = "החיבור נותק — ההורדה תתחדש אוטומטית")
    val httpError get() = t("The server refused the download", "Le serveur a refusé le téléchargement", "El servidor rechazó la descarga", "رفض الخادم التنزيل", he = "השרת סירב להורדה")
    val storageError get() = t("Could not write the file", "Impossible d’écrire le fichier", "No se pudo escribir el archivo", "تعذّرت كتابة الملف", he = "לא ניתן היה לכתוב את הקובץ")
    val connectionWhilePlaying get() = t(
        "A stream is playing: this download uses another connection of your subscription",
        "Un flux est en lecture : ce téléchargement utilise une autre connexion de votre abonnement",
        "Se está reproduciendo una emisión: esta descarga usa otra conexión de tu suscripción",
        "يتم تشغيل بث: هذا التنزيل يستخدم اتصالًا آخر من اشتراكك", he = "שידור פועל: ההורדה הזו משתמשת בחיבור נוסף של המנוי שלכם",
    )
    val connectionBusyDownloading get() = t(
        "A download is using your subscription’s connection — pause it if playback fails",
        "Un téléchargement utilise la connexion de votre abonnement — mettez-le en pause si la lecture échoue",
        "Una descarga está usando la conexión de tu suscripción: ponla en pausa si la reproducción falla",
        "يستخدم تنزيل اتصال اشتراكك — أوقفه مؤقتًا إذا فشل التشغيل", he = "הורדה משתמשת בחיבור של המנוי שלכם — השהו אותה אם ההפעלה נכשלת",
    )

    // Réglages
    val wifiOnly get() = t("Download on Wi-Fi only", "Télécharger en Wi-Fi uniquement", "Descargar solo con Wi-Fi", "التنزيل عبر Wi-Fi فقط", he = "הורדה ב-Wi-Fi בלבד")
    val wifiOnlyHint get() = t("Downloads wait for an unmetered network", "Les téléchargements attendent un réseau non limité", "Las descargas esperan una red sin límite", "تنتظر التنزيلات شبكة غير محدودة", he = "ההורדות ממתינות לרשת ללא הגבלת נתונים")
    val location get() = t("Download location", "Emplacement des téléchargements", "Ubicación de las descargas", "موقع التنزيلات", he = "מיקום ההורדות")
    val locationHint get() = t("Files stay private to Kanaani", "Les fichiers restent privés à Kanaani", "Los archivos son privados de Kanaani", "تبقى الملفات خاصة بتطبيق كنعاني", he = "הקבצים נשארים פרטיים לכנעני")
    val locationExternal get() = t("App storage (external)", "Stockage de l’appli (externe)", "Almacenamiento de la app (externo)", "تخزين التطبيق (خارجي)", he = "אחסון האפליקציה (חיצוני)")
    val locationInternal get() = t("Internal memory", "Mémoire interne", "Memoria interna", "الذاكرة الداخلية", he = "זיכרון פנימי")

    // Notification
    val notifChannel get() = t("Downloads", "Téléchargements", "Descargas", "التنزيلات", he = "הורדות")
    fun notifProgress(count: Int, percent: Int) = t(
        "$count downloading · $percent%", "$count en cours · $percent %", "$count en curso · $percent %", "$count قيد التنزيل · $percent٪", he = "$count בהורדה · $percent%",
    )
    val notifStarting get() = t("Preparing downloads…", "Préparation des téléchargements…", "Preparando descargas…", "جارٍ تحضير التنزيلات…", he = "מכין הורדות…")

    /** Message d'un code d'erreur enregistré (jamais le texte brut d'une exception). */
    fun error(code: String?): String = when (code) {
        DownloadError.NO_SPACE -> noSpace
        DownloadError.NOT_DOWNLOADABLE -> notDownloadable
        DownloadError.SOURCE_MISSING -> sourceMissing
        DownloadError.HTTP -> httpError
        DownloadError.STORAGE -> storageError
        else -> networkError
    }

    companion object {
        /**
         * Langue d'une table [Strings] : le rail TV ne passe que celle-ci à ses libellés (stringsFor est composable, donc
         * inutilisable ici) — reconnue à son libellé « Réglages », propre à chaque langue (vérifié par DownloadStringsTest).
         */
        fun of(s: Strings): DownloadStrings = DownloadStrings(
            when (s.navSettings) {
                "Paramètres" -> AppLang.French
                "Ajustes" -> AppLang.Spanish
                "الإعدادات" -> AppLang.Arabic
                "הגדרות" -> AppLang.Hebrew
                else -> AppLang.English
            },
        )
    }
}
