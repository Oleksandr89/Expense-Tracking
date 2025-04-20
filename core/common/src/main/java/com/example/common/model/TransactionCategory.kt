package com.example.common.model

sealed class TransactionCategory(val name: String) {
    data object RechargeBalance : TransactionCategory("Recharge Balance")
    data object Groceries : TransactionCategory("Groceries")
    data object Taxi : TransactionCategory("Taxi")
    data object Electronics : TransactionCategory("Electronics")
    data object Restaurant : TransactionCategory("Restaurant")
    data object Other : TransactionCategory("Other")
}