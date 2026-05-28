package com.example.domain.model.task5

import kotlinx.serialization.Serializable

@Serializable
data class PrizeDb(
    val id: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String,
    val detailLink: String,
    val laureates: List<LaureateDb> = emptyList()
)

@Serializable
data class LaureateDb(
    val id: Int,
    val prizeId: Int,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val portraitUrl: String
)

@Serializable
data class UserProfile(
    val id: Int,
    val username: String,
    val role: String
)

@Serializable
data class FavoritePrize(
    val prizeId: Int,
    val awardYear: Int,
    val category: String,
    val fullName: String,
    val motivation: String
)
