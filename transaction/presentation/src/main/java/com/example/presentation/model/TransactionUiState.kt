package com.example.presentation.model

import androidx.compose.runtime.Stable
import com.example.designSystem.R

@Stable
data class TransactionUiState(
    val navigateUp: Boolean = false,
    val isError: Boolean = false,
    val errorTextResId: Int = R.string.choose_category_enter_amount,
    val categories: List<Category> = emptyList()
)