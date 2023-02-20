package com.nutcrackers.landing

import dev.fritz2.core.*

import kotlinx.browser.window
import kotlin.js.Date

class BrowserTracker(){

    object LocaleStore : RootStore<Locale>(Locale(), id = "locale")

    object BrowserStore : RootStore<Browser>(Browser(), id = "browser")

    object ScenarioStore : RootStore<Scenario>(Scenario(), id = "scenario") {

        val save = handleAndEmit<Scenario> { s ->
            val sn = Scenario(browser = BrowserStore.current)
            emit(sn)
            sn
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

fun RenderContext.visitorsTable() {
    div("col-12") {
        div("card") {
            h5("card-header") {
                +"List of impressions"
            }
            div("card-body") {
                table("table") {
                    thead("thead-dark") {
                        th { +"OS" }
                        th { +"User Agent" }
                        th { +"Screen size" }
                        th { +"Time zone" }
                    }
                    tbody {
                        BrowserTracker.ScenarioListStore.data.renderEach { scenario ->
                            tr {
                                td { +scenario.browser!!.os!! }
                                td { +scenario.browser!!.userAgent!! }
                                td { +scenario.browser!!.screenSize!! }
                            //    td { +scenario.locale!!.timeZone!! }
                            }
                        }
                    }
                }
            }
        }
    }
}
