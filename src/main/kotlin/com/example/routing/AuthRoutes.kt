package com.example.routing

import com.example.data.dto.LoginRequest
import com.example.data.dto.LoginResponse
import com.example.data.repository.task5.PrizeDbRepository
import com.example.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(repo: PrizeDbRepository) {
    post("/auth/login") {
        val request = call.receive<LoginRequest>()
        if (repo.verifyPassword(request.username, request.password)) {
            val token = JwtConfig.generateToken(request.username)
            call.respond(LoginResponse(token = token, username = request.username))
        } else {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Неверный логин или пароль"))
        }
    }
}
