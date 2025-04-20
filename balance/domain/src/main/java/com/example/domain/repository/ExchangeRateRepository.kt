package com.example.domain.repository

import com.example.domain.model.ExchangeRate

interface ExchangeRateRepository {

    suspend fun fetchExchangeRate(): ExchangeRate

}