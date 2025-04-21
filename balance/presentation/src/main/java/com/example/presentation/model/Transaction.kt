package com.example.presentation.model

import androidx.compose.runtime.Stable
import com.example.common.model.TransactionCategory
import com.example.database.model.TransactionEntity
import com.example.common.model.TransactionType
import com.example.common.util.formatDayMonth
import com.example.common.util.formatTime

@Stable
data class Transaction(
    val time: String,
    val date: String,
    val amount: String,
    val category: TransactionCategory,
    val transactionType: TransactionType
)

fun TransactionEntity.toTransaction() = Transaction(
    time = timestamp.formatTime(),
    date = timestamp.formatDayMonth(),
    amount = when(transactionType) {
        TransactionType.Recharge.name -> "+${amount} BTC"
        TransactionType.Withdraw.name -> "-${amount} BTC"
        else -> amount
    },
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
