package com.example.domain.usecases

import com.example.domain.model.ExchangeRate
import com.example.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {

    suspend fun fetchExchangeRate(): ExchangeRate {
        val data = exchangeRateRepository.fetchExchangeRate()
        return data.copy(exchangeRate = "BTC/USD  ${data.exchangeRate}")
    }

}