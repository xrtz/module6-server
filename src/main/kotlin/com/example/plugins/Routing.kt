package com.example.plugins

import com.example.routing.authRoutes
import com.example.routing.prizeRoutes
import com.example.data.repository.PrizeRepositoryImpl
import com.example.domain.usecase.GetPrizeDetailUseCase
import com.example.domain.usecase.GetPrizesUseCase
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val prizeRepo = PrizeRepositoryImpl()
    val getPrizesUseCase = GetPrizesUseCase(prizeRepo)
    val getPrizeDetailUseCase = GetPrizeDetailUseCase(prizeRepo)

    routing {
        authRoutes()
        prizeRoutes(getPrizesUseCase, getPrizeDetailUseCase)
    }
}
