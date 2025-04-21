package com.example.presentation.model

import androidx.compose.runtime.Stable
import com.example.domain.model.BalanceModel
import com.example.domain.model.ExchangeRateModel

@Stable
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