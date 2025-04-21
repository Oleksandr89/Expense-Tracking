package com.example.domain.model

import com.example.common.model.TransactionCategory
import com.example.common.model.TransactionType
import com.example.database.model.TransactionEntity

data class TransactionModel(
    val timestamp: Long,
    val amount: String,
    val iconResId: Int,
    val category: TransactionCategory,
    val transactionType: TransactionType
)

fun String.toTransactionModel(
    iconResId: Int,
    category: TransactionCategory,
    transactionType: TransactionType
) = TransactionModel(
    timestamp = System.currentTimeMillis(),
    amount = this,
    iconResId = iconResId,
    category = category,
    transactionType = transactionType
)

fun TransactionModel.toTransactionEntity() = TransactionEntity(
    timestamp = timestamp,
    amount = amount,
    iconResId = iconResId,
    category = category.name,
    transactionType = transactionType.name
)

fun TransactionEntity.toTransactionModel() = TransactionModel(
    timestamp = timestamp,
    amount = amount,
    iconResId = iconResId,
    category = when (category) {
        TransactionCategory.RechargeBalance.name -> TransactionCategory.RechargeBalance
        TransactionCategory.Groceries.name -> TransactionCategory.Groceries
        TransactionCategory.Taxi.name -> TransactionCategory.Taxi
        TransactionCategory.Electronics.name -> TransactionCategory.Electronics
        else -> TransactionCategory.Other
    },
    transactionType = when (transactionType) {
        TransactionType.Recharge.name -> TransactionType.Recharge
        TransactionType.Withdraw.name -> TransactionType.Withdraw
        else -> TransactionType.Date
    }
)
