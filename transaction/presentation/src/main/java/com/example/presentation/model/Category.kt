package com.example.presentation.model

import com.example.common.model.TransactionCategory

data class Category (
    val type: TransactionCategory,
    val iconResId: Int,
    val categoryResId: Int
)