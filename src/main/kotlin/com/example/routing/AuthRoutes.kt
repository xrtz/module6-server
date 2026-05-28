package com.example.routing

import com.example.data.dto.LoginRequest
import com.example.data.dto.LoginResponse
import com.example.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val users = mapOf(
    "admin" to "admin123",
    "user" to "user123",
    "emilys" to "emilyspass"
)

fun Route.authRoutes() {
    post("/auth/login") {
        val request = call.receive<LoginRequest>()
        val expectedPassword = users[request.username]
        if (expectedPassword != null && expectedPassword == request.password) {
            val token = JwtConfig.generateToken(request.username)
            call.respond(LoginResponse(token = token, username = request.username))
        } else {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Неверный логин или пароль"))
        }
    }
}
