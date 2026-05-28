package com.example.data.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : IntIdTable("users") {
    val username = varchar("username", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val role = varchar("role", 50).default("user")
}

object PrizeTable : IntIdTable("prizes") {
    val awardYear = integer("award_year")
    val category = varchar("category", 100)
    val fullName = varchar("full_name", 255)
    val motivation = text("motivation").default("")
    val detailLink = varchar("detail_link", 500).default("")
}

object LaureateTable : IntIdTable("laureates") {
    val prizeId = reference("prize_id", PrizeTable, onDelete = ReferenceOption.CASCADE)
    val fullName = varchar("full_name", 255)
    val portion = varchar("portion", 20).default("")
    val motivation = text("motivation").default("")
    val portraitUrl = varchar("portrait_url", 500).default("")
}

object UserPrizeTable : Table("user_prizes") {
    val userId = reference("user_id", UserTable, onDelete = ReferenceOption.CASCADE)
    val prizeId = reference("prize_id", PrizeTable, onDelete = ReferenceOption.CASCADE)
    val addedAt = datetime("added_at")
    override val primaryKey = PrimaryKey(userId, prizeId)
}
