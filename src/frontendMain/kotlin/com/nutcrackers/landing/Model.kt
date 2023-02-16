package com.nutcrackers.landing

import dev.fritz2.core.Lenses

@Lenses
data class Event(
    val eventTime: String,
    val registerTime: String
)

@Lenses
data class ImpressionEvent(
    val eventSource: Event,
    val endpoint: String
)
