package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransactionRoute(
    modifier: Modifier = Modifier,
    onBackPressedCLick: () -> Unit
) {
    TransactionScreen(
        modifier = modifier,
        onBackPressedCLick = onBackPressedCLick
    )
}