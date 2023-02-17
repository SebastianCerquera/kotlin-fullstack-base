package com.nutcrackers.landing

import dev.fritz2.core.Lenses

@Lenses
data class Browser(
    val screenSize: String? = null,
    val screenColor: Int? = null,
    val userAgent: String? = null,
    val concurrency: Int? = null,
    val os: String? = null,
    val plugins: String? = null,
    val jsFonts: String? = null
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