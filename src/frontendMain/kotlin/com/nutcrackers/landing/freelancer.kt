package com.nutcrackers.landing

import dev.fritz2.core.render
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
fun main() {

    render("#target") {
        section {
            div("row") {
                +"ROE"
            }
            div("row mt-2") {
                +"TABLE"
            }
        }
    }
}
