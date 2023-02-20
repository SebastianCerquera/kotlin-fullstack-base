package com.nutcrackers.landing

import dev.fritz2.core.RenderContext
import dev.fritz2.core.src

fun RenderContext.wallpaper(
    image: String,
    tracker: BrowserTracker = BrowserTracker()
){
    val os = BrowserTracker.BrowserStore.map(Browser.os())
    val userAgent = BrowserTracker.BrowserStore.map(Browser.userAgent())
    val screenSize = BrowserTracker.BrowserStore.map(Browser.screenSize())
    val timeZone = BrowserTracker.LocaleStore.map(Locale.timeZone())

    img {
        src(image)

        os.update(tracker.getPlatform())
        userAgent.update(tracker.getUserAgent())
        screenSize.update(tracker.getScreenSize())
        timeZone.update(tracker.getTimeZone())

        console.log("Widget event")

        loads handledBy BrowserTracker.ScenarioStore.save
    }
}
