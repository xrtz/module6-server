package com.example

import com.example.data.repository.PrizeRepositoryImpl
import com.example.domain.usecase.GetPrizeDetailUseCase
import com.example.domain.usecase.GetPrizesUseCase
import com.example.plugins.*
import com.example.routing.authRoutes
import com.example.routing.prizeRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureAuthentication()
    configureCallLogging()
    configureCORS()
    configureStatusPages()

    val prizeRepo = PrizeRepositoryImpl()
    val getPrizesUseCase = GetPrizesUseCase(prizeRepo)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(prizeRepo)

    routing {
        authRoutes()
        prizeRoutes(getPrizesUseCase, getPrizeDetailUseCase)
    }
}
