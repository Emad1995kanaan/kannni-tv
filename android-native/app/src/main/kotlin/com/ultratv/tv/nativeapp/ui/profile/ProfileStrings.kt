package com.ultratv.tv.nativeapp.ui.profile

import com.ultratv.tv.nativeapp.i18n.AppLang

/** Textes de l'écran « Qui regarde ? » et de Réglages › Profils (EN / FR / ES / AR). */
class ProfileStrings(private val lang: AppLang) {
    private fun t(en: String, fr: String, es: String = en, ar: String = en, he: String = en) = when (lang) {
        AppLang.French -> fr
        AppLang.Spanish -> es
        AppLang.Arabic -> ar
        AppLang.Hebrew -> he
        else -> en
    }
    val whoWatching get() = t("Who's watching?", "Qui regarde ?", "¿Quién está viendo?", "من يشاهد؟", he = "מי צופה?")
    val add get() = t("Add", "Ajouter", "Añadir", "إضافة", he = "הוספה")
    val manage get() = t("Manage profiles", "Gérer les profils", "Gestionar perfiles", "إدارة الملفات الشخصية", he = "ניהול פרופילים")
    val done get() = t("Done", "Terminé", "Listo", "تم", he = "הושלם")
    val protectedHint get() = t("PIN protected", "Protégé par PIN", "Protegido con PIN", "محمي برمز PIN", he = "מוגן בקוד")
    val kidsHint get() = t("Kids", "Enfants", "Niños", "الأطفال", he = "ילדים")
    val lastHint get() = t("Last used", "Dernier utilisé", "Último usado", "آخر استخدام", he = "שימוש אחרון")
    val pinTitle get() = t("Protected profile", "Profil protégé", "Perfil protegido", "ملف محمي", he = "פרופיל מוגן")
    fun pinSubtitle(name: String) = t("Enter the PIN for $name", "Saisissez le code de $name", "Introduce el PIN de $name", "أدخل رمز PIN الخاص بـ $name", he = "הזינו את הקוד של $name")
    val wrongPin get() = t("Wrong PIN", "Code incorrect", "PIN incorrecto", "رمز PIN غير صحيح", he = "קוד שגוי")
    val settingsTitle get() = t("Profiles", "Profils", "Perfiles", "الملفات الشخصية", he = "פרופילים")
    val settingsSub get() = t(
        "Favorites, history and display are per profile. Sources and technical settings are shared.",
        "Favoris, historique et affichage sont propres à chaque profil. Sources et réglages techniques sont partagés.",
        "Favoritos, historial y pantalla son propios de cada perfil. Las fuentes y los ajustes técnicos se comparten.",
        "المفضلة والسجل والعرض خاصة بكل ملف شخصي. المصادر والإعدادات التقنية مشتركة.", he = "מועדפים, היסטוריה ותצוגה נשמרים לכל פרופיל. המקורות וההגדרות הטכניות משותפים.",
    )
    val newProfile get() = t("New profile", "Nouveau profil", "Nuevo perfil", "ملف شخصي جديد", he = "פרופיל חדש")
    val rename get() = t("Rename", "Renommer", "Renombrar", "إعادة التسمية", he = "שינוי שם")
    val color get() = t("Avatar color", "Couleur de l'avatar", "Color del avatar", "لون الصورة الرمزية", he = "צבע אווטאר")
    val kidsProfile get() = t("Kids profile", "Profil Enfants", "Perfil infantil", "ملف الأطفال", he = "פרופיל ילדים")
    val kidsHelp get() = t(
        "Adult categories hidden, parental control forced, technical settings locked",
        "Catégories adultes masquées, contrôle parental forcé, réglages techniques verrouillés",
        "Categorías para adultos ocultas, control parental forzado, ajustes técnicos bloqueados",
        "إخفاء فئات البالغين، فرض الرقابة الأبوية، قفل الإعدادات التقنية", he = "קטגוריות למבוגרים מוסתרות, בקרת הורים מופעלת, הגדרות טכניות נעולות",
    )
    val pin get() = t("Profile PIN", "Code du profil", "PIN del perfil", "رمز PIN للملف", he = "קוד פרופיל")
    val pinNone get() = t("None", "Aucun", "Ninguno", "بدون", he = "ללא")
    val pinSet get() = t("Enabled", "Activé", "Activado", "مفعّل", he = "מופעל")
    val pinRemove get() = t("Remove PIN", "Retirer le code", "Quitar el PIN", "إزالة الرمز", he = "הסרת קוד")
    val delete get() = t("Delete profile", "Supprimer le profil", "Eliminar perfil", "حذف الملف الشخصي", he = "מחיקת פרופיל")
    val deleteLast get() = t("The last profile cannot be deleted", "Le dernier profil ne peut pas être supprimé", "No se puede eliminar el último perfil", "لا يمكن حذف آخر ملف شخصي", he = "לא ניתן למחוק את הפרופיל האחרון")
    fun deleteConfirm(name: String) = t(
        "Delete \"$name\" and its favorites and history?",
        "Supprimer « $name » ainsi que ses favoris et son historique ?",
        "¿Eliminar «$name» junto con sus favoritos e historial?",
        "هل تريد حذف «$name» مع مفضلته وسجله؟", he = "למחוק את \"$name\" עם המועדפים וההיסטוריה שלו?",
    )
    val confirm get() = t("Delete", "Supprimer", "Eliminar", "حذف", he = "מחיקה")
    val cancel get() = t("Cancel", "Annuler", "Cancelar", "إلغاء", he = "ביטול")
    val startup get() = t("At startup", "Au démarrage", "Al iniciar", "عند التشغيل", he = "בהפעלה")
    val alwaysAsk get() = t("Always ask", "Toujours demander", "Preguntar siempre", "اسأل دائمًا", he = "לשאול תמיד")
    val lastProfile get() = t("Last profile", "Dernier profil", "Último perfil", "آخر ملف", he = "הפרופיל האחרון")
    val switchProfile get() = t("Switch profile", "Changer de profil", "Cambiar de perfil", "تبديل الملف", he = "החלפת פרופיל")
    val nameLabel get() = t("Name", "Nom", "Nombre", "الاسم", he = "שם")
    val save get() = t("Save", "Enregistrer", "Guardar", "حفظ", he = "שמירה")
    val kidsLocked get() = t("Locked for the Kids profile", "Verrouillé pour le profil Enfants", "Bloqueado para el perfil infantil", "مقفل لملف الأطفال", he = "נעול בפרופיל הילדים")
    val kidsNeedPin get() = t(
        "Set a parental PIN from another profile to unlock these settings.",
        "Définissez un code parental depuis un autre profil pour déverrouiller ces réglages.",
        "Define un PIN parental desde otro perfil para desbloquear estos ajustes.",
        "عيّن رمزًا أبويًا من ملف شخصي آخر لفتح هذه الإعدادات.", he = "הגדירו קוד הורים מפרופיל אחר כדי לפתוח את ההגדרות האלה.",
    )
    val colorNames get() = when (lang) {
        AppLang.French -> listOf("Bleu", "Rose", "Vert", "Ambre", "Violet", "Rouge", "Cyan", "Lime")
        AppLang.Spanish -> listOf("Azul", "Rosa", "Verde", "Ámbar", "Morado", "Rojo", "Cian", "Lima")
        AppLang.Arabic -> listOf("أزرق", "وردي", "أخضر", "عنبري", "بنفسجي", "أحمر", "سماوي", "ليموني")
        AppLang.Hebrew -> listOf("כחול (ברירת מחדל)", "ורוד", "ירוק", "ענבר", "סגול", "אדום", "תכלת", "ליים")
        else -> listOf("Blue", "Pink", "Green", "Amber", "Purple", "Red", "Cyan", "Lime")
    }
}
