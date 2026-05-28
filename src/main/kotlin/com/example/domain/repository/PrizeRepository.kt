package com.example.domain.repository

import com.example.domain.model.NobelPrize

interface PrizeRepository {
    fun getAllPrizes(): List<NobelPrize>
    fun getPrize(year: Int, category: String): NobelPrize?
}
