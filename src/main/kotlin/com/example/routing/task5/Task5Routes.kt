package com.example.routing.task5

import com.example.data.repository.task5.PrizeDbRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.task5Routes(repo: PrizeDbRepository) {
    // Публичный: список премий из БД
    get("/v2/prizes") {
        call.respond(repo.getAllPrizes())
    }

    authenticate("auth-jwt") {
        // Профиль текущего пользователя
        get("/users/me") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.getClaim("username").asString()
            val profile = repo.getUserProfile(username)
                ?: return@get call.respond(HttpStatusCode.NotFound, mapOf("error" to "Пользователь не найден"))
            call.respond(profile)
        }

        // Избранные премии
        get("/users/me/prizes") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.getClaim("username").asString()
            call.respond(repo.getUserFavorites(username))
        }

        // Добавить в избранное
        post("/users/me/prizes/{prizeId}") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.getClaim("username").asString()
            val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                ?: return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Неверный ID"))
            val success = repo.addFavorite(username, prizeId)
            if (success) call.respond(mapOf("message" to "Добавлено"))
            else call.respond(HttpStatusCode.NotFound, mapOf("error" to "Не найдено"))
        }

        // Удалить из избранного
        delete("/users/me/prizes/{prizeId}") {
            val principal = call.principal<JWTPrincipal>()!!
            val username = principal.payload.getClaim("username").asString()
            val prizeId = call.parameters["prizeId"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Неверный ID"))
            repo.removeFavorite(username, prizeId)
            call.respond(mapOf("message" to "Удалено"))
        }
    }
}
