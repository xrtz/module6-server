package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtConfig {
    private const val SECRET = "my-super-secret-key-for-jwt-signing-module6-2024"
    const val ISSUER = "module6-server"
    const val AUDIENCE = "mobile-app"
    private const val VALIDITY_MS = 30 * 60 * 1000L // 30 мин

    fun generateToken(username: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .withClaim("username", username)
        .withExpiresAt(Date(System.currentTimeMillis() + VALIDITY_MS))
        .sign(Algorithm.HMAC256(SECRET))

    val verifier: JWTVerifier = JWT.require(Algorithm.HMAC256(SECRET))
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()
}
