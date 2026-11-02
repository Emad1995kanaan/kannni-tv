package com.kanaani.ibo

/** Marque, générée depuis ibo-app/brand.properties. */
object Brand {
    val host: String = BuildConfig.BRAND_HOST.trim().trimEnd('/')
    val name: String = BuildConfig.BRAND_PROVIDER_NAME
    /** Serveur de secours : utilisé automatiquement si le principal ne répond pas. */
    val backupHost: String = BuildConfig.BRAND_BACKUP_HOST.trim().trimEnd('/')
    /** Principal puis secours. */
    val hosts: List<String> = listOf(host, backupHost).filter { it.isNotEmpty() }.distinct()
}
