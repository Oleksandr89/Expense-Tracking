package com.example.data.repository

import com.example.data.api.BalanceService
import com.example.domain.model.ExchangeRateModel
import com.example.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val service: BalanceService
) : ExchangeRateRepository {

    override suspend fun fetchExchangeRate(): ExchangeRateModel {
        try {
            val response = service.fetchExchangeRate()

            return if (response.isSuccessful) {
                ExchangeRateModel(response.body().toString())
            } else {
                ExchangeRateModel()
            }
        } catch (exception: Exception) {
            return ExchangeRateModel()
        }
    }

}