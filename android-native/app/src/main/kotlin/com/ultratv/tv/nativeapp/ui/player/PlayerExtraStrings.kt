package com.ultratv.tv.nativeapp.ui.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ultratv.tv.nativeapp.i18n.AppLang
import com.ultratv.tv.nativeapp.i18n.LocalDs

/**
 * Libellés des fonctions du lot B2 (zapping, veille, replay, timeshift, sous-titres), à part de
 * [com.ultratv.tv.nativeapp.i18n.DesignStrings] pour ne pas se marcher dessus avec les autres chantiers.
 */
class PlayerExtraStrings(val lang: AppLang) {
    private fun t(en: String, fr: String, es: String = en, ar: String = en, he: String = en): String = when (lang) {
        AppLang.French -> fr
        AppLang.Spanish -> es
        AppLang.Arabic -> ar
        AppLang.Hebrew -> he
        else -> en
    }

    // Zapping
    val recent get() = t("RECENT", "RÉCENTES", "RECIENTES", "الأخيرة", he = "אחרונים")
    val zapHint get() = t("Back: previous channel · ▲▼ zap · 0-9 number", "Retour : chaîne précédente · ▲▼ zapper · 0-9 numéro", "Atrás: canal anterior · ▲▼ zapear · 0-9 número", "رجوع: القناة السابقة · ▲▼ تنقّل · 0-9 رقم", he = "חזרה: הערוץ הקודם · ▲▼ מעבר · 0-9 מספר")
    val noChannelNumber get() = t("No such channel", "Aucune chaîne à ce numéro", "Ningún canal con ese número", "لا توجد قناة بهذا الرقم", he = "אין ערוץ כזה")

    // Replay
    val fromStart get() = t("From the start", "Depuis le début", "Desde el principio", "من البداية", he = "מההתחלה")
    val backToLive get() = t("Back to live", "Revenir au direct", "Volver al directo", "العودة إلى البث المباشر", he = "חזרה לשידור החי")

    // Sous-titres
    val subsPill get() = t("Subtitles", "Sous-titres", "Subtítulos", "الترجمة", he = "כתוביות")
    val subtitlesTitle get() = t("Subtitles", "Sous-titres", "Subtítulos", "الترجمة", he = "כתוביות")
    val previewText get() = t("Subtitle preview\nwith your settings", "Aperçu des sous-titres\navec vos réglages", "Vista previa de subtítulos\ncon tus ajustes", "معاينة الترجمة\nبإعداداتك", he = "תצוגה מקדימה של כתוביות\nעם ההגדרות שלכם")
    val off get() = t("Off", "Désactivés", "Desactivados", "متوقفة", he = "כבוי")
    val track get() = t("Track", "Piste", "Pista", "المسار", he = "רצועה")
    val size get() = t("Size", "Taille", "Tamaño", "الحجم", he = "גודל")
    val color get() = t("Colour", "Couleur", "Color", "اللون", he = "צבע")
    val background get() = t("Background", "Fond", "Fondo", "الخلفية", he = "רקע")
    val outline get() = t("Outline", "Contour", "Contorno", "الحدود", he = "קו מתאר")
    val position get() = t("Position", "Position", "Posición", "الموضع", he = "מיקום")
    val delay get() = t("Offset", "Décalage", "Desfase", "التأخير", he = "היסט")
    val delayVlcOnly get() = t("VLC only", "VLC uniquement", "Solo VLC", "VLC فقط", he = "VLC בלבד")
    val audioLangs get() = t("Audio languages", "Langues audio", "Idiomas de audio", "لغات الصوت", he = "שפות שמע")
    val textLangs get() = t("Subtitle languages", "Langues sous-titres", "Idiomas de subtítulos", "لغات الترجمة", he = "שפות כתוביות")
    val langOrderHint get() = t("Pick in order of preference", "Choisir dans l'ordre de préférence", "Elige por orden de preferencia", "اختر بترتيب التفضيل", he = "בחרו לפי סדר העדפה")
    val searchOnline get() = t("Search subtitles online", "Chercher des sous-titres en ligne", "Buscar subtítulos en línea", "البحث عن ترجمات", he = "חיפוש כתוביות ברשת")
    val searchNeedsProxy get() = t("Pair this device with your Worker to enable it", "Appairez l'appareil à votre Worker pour l'activer", "Vincula el dispositivo a tu Worker para activarlo", "اربط الجهاز بالـ Worker لتفعيله", he = "צמדו את המכשיר ל-Worker שלכם כדי להפעיל")
    val searchMoviesOnly get() = t("Available for movies only", "Disponible pour les films uniquement", "Solo para películas", "متاح للأفلام فقط", he = "זמין לסרטים בלבד")
    val subtitlesNotConfigured get() = t("Subtitle search is not enabled on your Worker (OpenSubtitles key missing)", "La recherche de sous-titres n'est pas activée sur votre Worker (clé OpenSubtitles absente)", "La búsqueda de subtítulos no está activada en su Worker (falta la clave de OpenSubtitles)", "البحث عن الترجمات غير مفعّل على الـ Worker الخاص بك (مفتاح OpenSubtitles غير موجود)", he = "חיפוש כתוביות לא מופעל ב-Worker שלכם (חסר מפתח OpenSubtitles)")
    val searching get() = t("Searching…", "Recherche…", "Buscando…", "جارٍ البحث…", he = "מחפש…")
    val noSubtitleFound get() = t("No subtitles found", "Aucun sous-titre trouvé", "No se encontraron subtítulos", "لا توجد ترجمات", he = "לא נמצאו כתוביות")
    val subtitleAdded get() = t("Subtitles added", "Sous-titres ajoutés", "Subtítulos añadidos", "تمت إضافة الترجمة", he = "הכתוביות נוספו")
    val back get() = t("Back", "Retour", "Atrás", "رجوع", he = "חזרה")
    fun sizeName(v: com.ultratv.tv.nativeapp.data.subtitles.SubSize) = when (v) {
        com.ultratv.tv.nativeapp.data.subtitles.SubSize.SMALL -> t("Small", "Petite", "Pequeño", "صغير", he = "קטן")
        com.ultratv.tv.nativeapp.data.subtitles.SubSize.MEDIUM -> t("Medium", "Moyenne", "Mediano", "متوسط", he = "בינוני")
        com.ultratv.tv.nativeapp.data.subtitles.SubSize.LARGE -> t("Large", "Grande", "Grande", "كبير", he = "גדול")
        com.ultratv.tv.nativeapp.data.subtitles.SubSize.XLARGE -> t("Extra large", "Très grande", "Muy grande", "كبير جدًا", he = "גדול מאוד")
    }
    fun colorName(v: com.ultratv.tv.nativeapp.data.subtitles.SubColor) = when (v) {
        com.ultratv.tv.nativeapp.data.subtitles.SubColor.WHITE -> t("White", "Blanc", "Blanco", "أبيض", he = "לבן")
        com.ultratv.tv.nativeapp.data.subtitles.SubColor.YELLOW -> t("Yellow", "Jaune", "Amarillo", "أصفر", he = "צהוב")
        com.ultratv.tv.nativeapp.data.subtitles.SubColor.CYAN -> t("Cyan", "Cyan", "Cian", "سماوي", he = "תכלת")
        com.ultratv.tv.nativeapp.data.subtitles.SubColor.GREEN -> t("Green", "Vert", "Verde", "أخضر", he = "ירוק")
    }
    fun backgroundName(v: com.ultratv.tv.nativeapp.data.subtitles.SubBackground) = when (v) {
        com.ultratv.tv.nativeapp.data.subtitles.SubBackground.NONE -> t("None", "Aucun", "Ninguno", "بلا", he = "ללא")
        com.ultratv.tv.nativeapp.data.subtitles.SubBackground.SEMI -> t("Semi-transparent", "Semi-transparent", "Semitransparente", "شبه شفاف", he = "שקוף למחצה")
        com.ultratv.tv.nativeapp.data.subtitles.SubBackground.OPAQUE -> t("Opaque", "Opaque", "Opaco", "معتم", he = "אטום")
    }
    fun outlineName(v: com.ultratv.tv.nativeapp.data.subtitles.SubOutline) = when (v) {
        com.ultratv.tv.nativeapp.data.subtitles.SubOutline.NONE -> t("None", "Aucun", "Ninguno", "بلا", he = "ללא")
        com.ultratv.tv.nativeapp.data.subtitles.SubOutline.THIN -> t("Thin", "Fin", "Fino", "رفيع", he = "דק")
        com.ultratv.tv.nativeapp.data.subtitles.SubOutline.THICK -> t("Thick", "Épais", "Grueso", "سميك", he = "עבה")
    }
    fun positionName(v: com.ultratv.tv.nativeapp.data.subtitles.SubPosition) = when (v) {
        com.ultratv.tv.nativeapp.data.subtitles.SubPosition.BOTTOM -> t("Bottom", "Bas", "Abajo", "أسفل", he = "למטה")
        com.ultratv.tv.nativeapp.data.subtitles.SubPosition.RAISED -> t("Raised", "Relevé", "Elevado", "مرتفع", he = "מוגבה")
        com.ultratv.tv.nativeapp.data.subtitles.SubPosition.HIGH -> t("High", "Haut", "Alto", "عالٍ", he = "גבוה")
    }

    // Pause du direct
    val delayed get() = t("TIME-SHIFTED", "EN DIFFÉRÉ", "EN DIFERIDO", "مؤجّل", he = "בהשהיה")
    val directMark get() = t("LIVE", "DIRECT", "DIRECTO", "مباشر", he = "חי")
    val tsHls get() = t("Pause is unavailable on this stream (HLS)", "Pause du direct indisponible sur ce flux (HLS)", "Pausa no disponible en esta señal (HLS)", "الإيقاف المؤقت غير متاح لهذا البث (HLS)", he = "השהיה לא זמינה בשידור הזה (HLS)")
    val tsNoSpace get() = t("Not enough free space to pause live TV", "Espace libre insuffisant pour mettre le direct en pause", "Espacio libre insuficiente para pausar el directo", "مساحة التخزين غير كافية لإيقاف البث مؤقتًا", he = "אין מספיק מקום פנוי להשהיית השידור החי")
    val tsFailed get() = t("Live buffer interrupted, back to live", "Tampon interrompu, retour au direct", "Búfer interrumpido, volviendo al directo", "انقطع التخزين المؤقت، العودة إلى المباشر", he = "החוצץ החי נקטע, חוזרים לשידור החי")

    // Veille
    val sleepPill get() = t("Sleep", "Veille", "Dormir", "السكون", he = "שינה")
    /** Bandeau du direct (après un zap) : les commandes ne sont pas encore actives, on dit comment les atteindre. */
    val bannerHint get() = t("◀ ▶ Controls  ·  OK Channels  ·  ▲ ▼ Zap", "◀ ▶ Commandes  ·  OK Chaînes  ·  ▲ ▼ Zapper", "◀ ▶ Controles  ·  OK Canales  ·  ▲ ▼ Zapear", "◀ ▶ الأوامر  ·  OK القنوات  ·  ▲ ▼ التنقّل", he = "◀ ▶ פקדים  ·  OK ערוצים  ·  ▲ ▼ מעבר")
    val sleepTitle get() = t("Sleep timer", "Minuterie de veille", "Temporizador", "مؤقّت السكون", he = "טיימר שינה")
    val sleepEndOfProgramme get() = t("End of programme", "Fin du programme", "Fin del programa", "نهاية البرنامج", he = "סוף התוכנית")
    val sleepOff get() = t("Cancel timer", "Annuler la minuterie", "Cancelar", "إلغاء المؤقّت", he = "ביטול טיימר")
    val stillThere get() = t("Still there?", "Toujours là ?", "¿Sigues ahí?", "هل ما زلت هنا؟", he = "עדיין כאן?")
    val imHere get() = t("I'm here", "Je suis là", "Sigo aquí", "أنا هنا", he = "אני כאן")
    val sleepNow get() = t("Stop now", "Arrêter maintenant", "Parar ahora", "إيقاف الآن", he = "לעצור עכשיו")
    val close get() = t("Close", "Fermer", "Cerrar", "إغلاق", he = "סגירה")
    fun sleepStopsIn(s: Int) = when (lang) { AppLang.French -> "Arrêt dans $s s"; AppLang.Spanish -> "Se detiene en $s s"; AppLang.Arabic -> "الإيقاف بعد $s ث"; AppLang.Hebrew -> "נעצר בעוד $s שנ׳"; else -> "Stopping in $s s" }
}

@Composable
fun playerExtras(): PlayerExtraStrings {
    val lang = LocalDs.current.lang
    return remember(lang) { PlayerExtraStrings(lang) }
}
