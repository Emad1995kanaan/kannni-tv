package com.ultratv.tv.nativeapp

/**
 * Configuration de la marque, générée depuis android-native/brand.properties.
 *
 * Quand [host] est renseigné, l'application est « verrouillée » sur ce serveur Xtream :
 * l'utilisateur ne saisit que son identifiant et son mot de passe, et les autres types
 * de source (M3U, Stalker, cloud) sont masqués.
 */
object Brand {
    val host: String = BuildConfig.BRAND_HOST.trim().trimEnd('/')
    val providerName: String = BuildConfig.BRAND_PROVIDER_NAME
    val updateRepo: String = BuildConfig.BRAND_UPDATE_REPO.trim()

    /** Accueil à grandes tuiles façon IBO Player (BRAND_HOME_STYLE=ibo). */
    val iboHome: Boolean = BuildConfig.BRAND_HOME_STYLE.trim().equals("ibo", ignoreCase = true)

    /** Vrai quand l'app est liée à un seul serveur Xtream. */
    val locked: Boolean get() = host.isNotEmpty()
}
