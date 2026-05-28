package com.example.routing

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private fun resourceText(path: String): String =
    Thread.currentThread().contextClassLoader.getResource(path)?.readText()
        ?: error("Resource not found: $path")

fun Route.docsRoutes() {
    get("/openapi.yaml") {
        call.respondText(
            text = resourceText("openapi/openapi.yaml"),
            contentType = ContentType("application", "yaml")
        )
    }
    get("/docs") {
        call.respondText(
            text = resourceText("openapi/redoc.html"),
            contentType = ContentType.Text.Html
        )
    }
    get("/swagger") {
        call.respondText(
            text = resourceText("openapi/swagger.html"),
            contentType = ContentType.Text.Html
        )
    }
}
