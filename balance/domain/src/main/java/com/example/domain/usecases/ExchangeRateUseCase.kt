package com.example.domain.usecases

import com.example.domain.model.ExchangeRateModel
import com.example.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class ExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {

    suspend fun fetchExchangeRate(): ExchangeRateModel =
        exchangeRateRepository.fetchExchangeRate()

}