package com.example.domain.model

data class Transaction(
    val timestamp: Long,
    val amount: String,
    val category: String,
    val transactionType: String
)