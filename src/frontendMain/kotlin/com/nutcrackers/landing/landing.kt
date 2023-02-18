package com.nutcrackers.landing

import dev.fritz2.core.render
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
fun main() {
    val browserTracker = BrowserTracker()

    render("#target"){
        wallpaper("https://c4.wallpaperflare.com/wallpaper/586/603/742/minimalism-4k-for-mac-desktop-wallpaper-preview.jpg")
    }
}
