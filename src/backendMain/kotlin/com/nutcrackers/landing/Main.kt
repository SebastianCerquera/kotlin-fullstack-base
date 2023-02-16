package com.nutcrackers.landing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableR2dbcRepositories
@SpringBootApplication
class LandingApplication

fun main(args: Array<String>) {
    runApplication<LandingApplication>(*args)
}
