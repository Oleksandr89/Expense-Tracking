package com.example.data.repository

import com.example.data.api.BalanceService
import com.example.domain.model.ExchangeRate
import com.example.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val service: BalanceService
) : ExchangeRateRepository {

    override suspend fun fetchExchangeRate(): ExchangeRate {
        val response = service.fetchExchangeRate()

        return if (response.isSuccessful) {
            ExchangeRate(response.body().toString())
        } else {
            ExchangeRate("")
        }
    }

}