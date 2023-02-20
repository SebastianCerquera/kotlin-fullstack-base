package com.nutcrackers.landing

import dev.fritz2.core.*
import kotlinx.browser.window
import kotlin.js.Date

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
