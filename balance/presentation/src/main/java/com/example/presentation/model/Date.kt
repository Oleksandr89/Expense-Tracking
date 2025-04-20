package com.example.presentation.model

import com.example.common.model.TransactionType

fun addDateItem(
    before: Transaction?,
    after: Transaction?,
): Transaction? {
    return if (before?.date != after?.date) {
        after?.copy(transactionType = TransactionType.Date)
    } else {
        null
    }
}