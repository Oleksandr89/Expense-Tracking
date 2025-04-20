package com.example.presentation.model

data class TransactionUiState(
    val navigateUp: Boolean = false,
    val isError: Boolean = false,
    val categories: List<Category> = emptyList()
)