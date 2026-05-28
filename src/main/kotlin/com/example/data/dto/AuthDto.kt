package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String, val username: String)

@Serializable
data class ErrorResponse(val error: String)
