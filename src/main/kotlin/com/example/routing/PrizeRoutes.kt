package com.example.routing

import com.example.domain.usecase.GetPrizeDetailUseCase
import com.example.domain.usecase.GetPrizesUseCase
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prizeRoutes(
    getPrizesUseCase: GetPrizesUseCase,
    getPrizeDetailUseCase: GetPrizeDetailUseCase
) {
    authenticate("auth-jwt") {
        get("/prizes") {
            call.respond(getPrizesUseCase())
        }

        get("/prizes/{year}/{category}") {
            val year = call.parameters["year"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Неверный год"))
            val category = call.parameters["category"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Укажите категорию"))

            val prize = getPrizeDetailUseCase(year, category)
                ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Премия не найдена"))

            call.respond(prize)
        }

        get("/prizes/{year}/{category}/laureates") {
            val year = call.parameters["year"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Неверный год"))
            val category = call.parameters["category"]
                ?: return@get call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Укажите категорию"))

            val prize = getPrizeDetailUseCase(year, category)
                ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Премия не найдена"))

            call.respond(prize.laureates)
        }
    }
}
