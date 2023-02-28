package com.nutcrackers.landing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.nutcrackers.landing.repository.model")
class LandingApplication

fun main(args: Array<String>) {
    runApplication<LandingApplication>(*args)
}
