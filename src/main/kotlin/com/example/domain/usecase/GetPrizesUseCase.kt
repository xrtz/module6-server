package com.example.domain.usecase

import com.example.domain.model.NobelPrize
import com.example.domain.repository.PrizeRepository

class GetPrizesUseCase(private val repository: PrizeRepository) {
    operator fun invoke(): List<NobelPrize> = repository.getAllPrizes()
}

class GetPrizeDetailUseCase(private val repository: PrizeRepository) {
    operator fun invoke(year: Int, category: String): NobelPrize? = repository.getPrize(year, category)
}
