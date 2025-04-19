package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BalanceRoute(
    modifier: Modifier = Modifier,
    onAddTransactionCLick: () -> Unit
) {
    BalanceScreen(
        modifier = modifier,
        onAddTransactionCLick = onAddTransactionCLick
    )
}