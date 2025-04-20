package com.example.presentation.model

import com.example.domain.model.BalanceModel
import com.example.domain.model.ExchangeRateModel

data class BalanceUiState(
    val balance: String = "",
    val exchangeRate: String = ""
)

internal fun createBalanceUiState(
    balance: BalanceModel,
    exchange: ExchangeRateModel
) = BalanceUiState(
    balance = "${balance.amount} BTC",
    exchangeRate = "BTC/USD  ${exchange.exchangeRate}"
)