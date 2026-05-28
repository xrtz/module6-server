package com.example.data.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(jdbcUrl: String, user: String, password: String) {
        val config = HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            driverClassName = "org.postgresql.Driver"
            username = user
            this.password = password
            maximumPoolSize = 10
            minimumIdle = 2
            idleTimeout = 300_000
            maxLifetime = 1_800_000
            connectionTimeout = 30_000
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
        transaction {
            SchemaUtils.create(
                UserTable,
                PrizeTable,
                LaureateTable,
                UserPrizeTable
            )
        }
    }
}
