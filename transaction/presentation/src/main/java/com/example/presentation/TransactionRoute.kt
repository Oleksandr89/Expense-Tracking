package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.presentation.view.TransactionScreen
import com.example.presentation.viewmodel.TransactionViewModel

@Composable
fun TransactionRoute(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = hiltViewModel(),
    onBackPressedCLick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value) {
        if (uiState.value.navigateUp) {
            onBackPressedCLick.invoke()
        }
    }

    TransactionScreen(
        modifier = modifier,
        uiState = uiState,
        onBackPressedCLick = onBackPressedCLick,
        onAddTransactionCLick = viewModel::addTransaction
    )
}