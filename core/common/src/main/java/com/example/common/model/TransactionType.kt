package com.example.common.model

sealed class TransactionType(val name: String) {
    data object Recharge: TransactionType("Recharge")
    data object Withdraw: TransactionType("Withdraw")
    data object Date: TransactionType("Date")
}
