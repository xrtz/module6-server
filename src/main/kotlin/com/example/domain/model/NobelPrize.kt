package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Laureate(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String
)

@Serializable
data class NobelPrize(
    val id: String,
    val year: Int,
    val category: String,
    val laureates: List<Laureate>
)
