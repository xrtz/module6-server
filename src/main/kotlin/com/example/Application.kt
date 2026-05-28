package com.example

import com.example.data.database.DatabaseFactory
import com.example.data.repository.PrizeRepositoryImpl
import com.example.data.repository.task5.PrizeDbRepository
import com.example.plugins.*
import com.example.routing.authRoutes
import com.example.routing.docsRoutes
import com.example.routing.task5.task5Routes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking

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

    val jdbcUrl = System.getenv("JDBC_URL") ?: error("JDBC_URL not set")
    val dbUser = System.getenv("DB_USER") ?: error("DB_USER not set")
    val dbPassword = System.getenv("DB_PASSWORD") ?: error("DB_PASSWORD not set")

    DatabaseFactory.init(jdbcUrl, dbUser, dbPassword)
    val repo = PrizeDbRepository()

    runBlocking {
        repo.seedData(PrizeRepositoryImpl().getAllPrizes())
        repo.ensureUser("admin", "admin123")
        repo.ensureUser("user", "user123")
    }

    routing {
        authRoutes(repo)
        task5Routes(repo)
        docsRoutes()
    }
}
