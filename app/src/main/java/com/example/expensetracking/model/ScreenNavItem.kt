package com.example.expensetracking.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenNavItem {

    @Serializable
    data object BalanceScreen : ScreenNavItem

    @Serializable
    data object TransactionScreen : ScreenNavItem
}