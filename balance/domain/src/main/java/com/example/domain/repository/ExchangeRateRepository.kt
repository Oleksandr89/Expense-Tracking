package com.example.domain.repository

import com.example.domain.model.ExchangeRateModel

interface ExchangeRateRepository {

    suspend fun fetchExchangeRate(): ExchangeRateModel

}