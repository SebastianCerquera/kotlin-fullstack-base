package com.nutcrackers.landing

import dev.fritz2.core.Lenses

@Lenses
data class Browser(
    val screenSize: String?,
    val screenColor: Int?,
    val userAgent: String?,
    val concurrency: Int?,
    val os: String?,
    val plugins: String?,
    val jsFonts: String?
) {
    companion object
}

@Lenses
data class Session(
    val id: String = "",
    val firstTime: Boolean = false
) {
    companion object
}

@Lenses
data class Locale(
    val ipAddress: String = "0.0.0.0",
    val timeZone: String? = null,
    val timeZoneOffset: String? = null
) {
    companion object
}

@Lenses
data class Scenario(
    val browser: Browser? = null,
    val session: Session? = null,
    val locale: Locale? = null
){
    companion object
}