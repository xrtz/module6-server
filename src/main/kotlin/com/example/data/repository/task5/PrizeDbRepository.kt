package com.example.data.repository.task5

import com.example.data.database.*
import com.example.domain.model.task5.FavoritePrize
import com.example.domain.model.task5.LaureateDb
import com.example.domain.model.task5.PrizeDb
import com.example.domain.model.task5.UserProfile
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime

class PrizeDbRepository {

    suspend fun getAllPrizes(): List<PrizeDb> = newSuspendedTransaction {
        val prizes = PrizeTable.selectAll().map { row ->
            PrizeDb(
                id = row[PrizeTable.id].value,
                awardYear = row[PrizeTable.awardYear],
                category = row[PrizeTable.category],
                fullName = row[PrizeTable.fullName],
                motivation = row[PrizeTable.motivation],
                detailLink = row[PrizeTable.detailLink]
            )
        }
        prizes.map { prize ->
            val laureates = LaureateTable
                .selectAll().where { LaureateTable.prizeId eq prize.id }
                .map { row ->
                    LaureateDb(
                        id = row[LaureateTable.id].value,
                        prizeId = prize.id,
                        fullName = row[LaureateTable.fullName],
                        portion = row[LaureateTable.portion],
                        motivation = row[LaureateTable.motivation],
                        portraitUrl = row[LaureateTable.portraitUrl]
                    )
                }
            prize.copy(laureates = laureates)
        }
    }

    suspend fun getPrizeById(id: Int): PrizeDb? = newSuspendedTransaction {
        val row = PrizeTable.selectAll().where { PrizeTable.id eq id }.singleOrNull()
            ?: return@newSuspendedTransaction null
        val prize = PrizeDb(
            id = row[PrizeTable.id].value,
            awardYear = row[PrizeTable.awardYear],
            category = row[PrizeTable.category],
            fullName = row[PrizeTable.fullName],
            motivation = row[PrizeTable.motivation],
            detailLink = row[PrizeTable.detailLink]
        )
        val laureates = LaureateTable
            .selectAll().where { LaureateTable.prizeId eq prize.id }
            .map { r ->
                LaureateDb(
                    id = r[LaureateTable.id].value,
                    prizeId = prize.id,
                    fullName = r[LaureateTable.fullName],
                    portion = r[LaureateTable.portion],
                    motivation = r[LaureateTable.motivation],
                    portraitUrl = r[LaureateTable.portraitUrl]
                )
            }
        prize.copy(laureates = laureates)
    }

    suspend fun seedData(prizes: List<com.example.domain.model.NobelPrize>) = newSuspendedTransaction {
        val existing = PrizeTable.selectAll().count()
        if (existing > 0) return@newSuspendedTransaction
        prizes.forEach { prize ->
            val newPrizeId = PrizeTable.insertAndGetId {
                it[awardYear] = prize.year
                it[category] = prize.category
                it[fullName] = prize.category.replaceFirstChar { c -> c.uppercase() } + " " + prize.year
                it[motivation] = prize.laureates.firstOrNull()?.motivation ?: ""
                it[detailLink] = "https://www.nobelprize.org/prizes/${prize.category}/${prize.year}/"
            }
            prize.laureates.forEach { laureate ->
                LaureateTable.insert {
                    it[LaureateTable.prizeId] = newPrizeId
                    it[fullName] = laureate.fullName
                    it[portion] = laureate.portion
                    it[motivation] = laureate.motivation
                    it[portraitUrl] = ""
                }
            }
        }
    }

    suspend fun getUserProfile(username: String): UserProfile? = newSuspendedTransaction {
        UserTable.selectAll().where { UserTable.username eq username }.singleOrNull()?.let { row ->
            UserProfile(
                id = row[UserTable.id].value,
                username = row[UserTable.username],
                role = row[UserTable.role]
            )
        }
    }

    suspend fun getUserFavorites(username: String): List<FavoritePrize> = newSuspendedTransaction {
        val user = UserTable.selectAll().where { UserTable.username eq username }.singleOrNull()
            ?: return@newSuspendedTransaction emptyList()
        val userId = user[UserTable.id].value
        (UserPrizeTable innerJoin PrizeTable)
            .selectAll().where { UserPrizeTable.userId eq userId }
            .map { row ->
                FavoritePrize(
                    prizeId = row[PrizeTable.id].value,
                    awardYear = row[PrizeTable.awardYear],
                    category = row[PrizeTable.category],
                    fullName = row[PrizeTable.fullName],
                    motivation = row[PrizeTable.motivation]
                )
            }
    }

    suspend fun addFavorite(username: String, prizeId: Int): Boolean = newSuspendedTransaction {
        val user = UserTable.selectAll().where { UserTable.username eq username }.singleOrNull()
            ?: return@newSuspendedTransaction false
        val userId = user[UserTable.id].value
        val exists = UserPrizeTable.selectAll().where {
            (UserPrizeTable.userId eq userId) and (UserPrizeTable.prizeId eq prizeId)
        }.count() > 0
        if (!exists) {
            UserPrizeTable.insert {
                it[UserPrizeTable.userId] = userId
                it[UserPrizeTable.prizeId] = prizeId
                it[addedAt] = LocalDateTime.now()
            }
        }
        true
    }

    suspend fun removeFavorite(username: String, prizeId: Int): Boolean = newSuspendedTransaction {
        val user = UserTable.selectAll().where { UserTable.username eq username }.singleOrNull()
            ?: return@newSuspendedTransaction false
        val userIdVal = user[UserTable.id].value
        val prizeIdVal = prizeId
        UserPrizeTable.deleteWhere {
            SqlExpressionBuilder.run {
                (UserPrizeTable.userId eq userIdVal) and (UserPrizeTable.prizeId eq prizeIdVal)
            }
        }
        true
    }

    suspend fun ensureUser(username: String, passwordHash: String) = newSuspendedTransaction {
        val exists = UserTable.selectAll().where { UserTable.username eq username }.count() > 0
        if (!exists) {
            UserTable.insert {
                it[UserTable.username] = username
                it[UserTable.passwordHash] = passwordHash
                it[role] = "user"
            }
        }
    }
}
