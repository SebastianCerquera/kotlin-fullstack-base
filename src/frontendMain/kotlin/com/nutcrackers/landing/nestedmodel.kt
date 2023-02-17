package com.nutcrackers.landing

import dev.fritz2.core.*
import kotlinx.browser.window
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.js.Date

object LocaleStore : RootStore<Locale>(Locale(), id = "locale") {
    val save = handleAndEmit<Locale> { l ->
        emit(l)
        l
    }
}

object BrowserStore : RootStore<Browser>(Browser(), id = "browser") {
    val save = handleAndEmit<Browser> { b ->
        emit(b)
        b
    }
}

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

fun RenderContext.visitorsDetails(
    os: Store<String?>,
    userAgent: Store<String?>,
    screenSize: Store<String?>,
    timeZone: Store<String?>
) {
    os.update(window.navigator.platform)
    userAgent.update(window.navigator.userAgent)
    screenSize.update("${window.screen.width}x${window.screen.height}")
    timeZone.update(Date().toTimeString().slice(IntRange(9, Int.MAX_VALUE)))

    div("col-12") {
        div("card") {
            h5("card-header") {
                +"Visitor details"
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
                            tr {
                                td{ os.data.renderText() }
                                td { userAgent.data.renderText() }
                                td { screenSize.data.renderText() }
                                td { timeZone.data.renderText() }
                            }
                    }
                }
            }
        }
    }
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
                        ScenarioListStore.data.renderEach { scenario ->
                            tr {
                                td { +scenario.browser!!.os!! }
                                td { +scenario.browser!!.userAgent!! }
                                td { +scenario.browser!!.screenSize!! }
                                td { +scenario.locale!!.timeZone!! }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
fun main() {
    val os = BrowserStore.map(Browser.os())
    val userAgent = BrowserStore.map(Browser.userAgent())
    val screenSize = BrowserStore.map(Browser.screenSize())
    val timeZone = LocaleStore.map(Locale.timeZone())

    val scenario = Scenario(
        browser = BrowserStore.current,
        locale = LocaleStore.current
    )

    render("#target") {
        section {
            div("row") {
                visitorsDetails(
                    os, userAgent, screenSize, timeZone
                )
            }
            div("row mt-2") {
                visitorsTable()
            }
        }
    }
}