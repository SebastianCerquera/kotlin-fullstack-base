package com.nutcrackers.landing

import dev.fritz2.core.*

import kotlinx.browser.window
import kotlin.js.Date

class BrowserTracker(){

    object LocaleStore : RootStore<Locale>(Locale(), id = "locale")

    object BrowserStore : RootStore<Browser>(Browser(), id = "browser")

    object ScenarioStore : RootStore<Scenario>(Scenario(), id = "scenario") {
        val save = handleAndEmit<Scenario> { s ->
            emit(s)
            s
        }
    }

    object ScenarioListStore : RootStore<List<Scenario>>(emptyList(), id = "list") {
        private val add: SimpleHandler<Scenario> = handle { list, scenario ->
            list + scenario
        }

        init {
            //connect the two stores
            ScenarioStore.save handledBy add
        }
    }

    fun getPlatform(): String? = window.navigator.platform

    fun getUserAgent(): String? = window.navigator.userAgent

    fun getScreenSize(): String? = "${window.screen.width}x${window.screen.height}"

    fun getTimeZone(): String? = Date().toTimeString().slice(IntRange(9, Int.MAX_VALUE))
}

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

        loads handledBy BrowserTracker.ScenarioStore.save
    }
}