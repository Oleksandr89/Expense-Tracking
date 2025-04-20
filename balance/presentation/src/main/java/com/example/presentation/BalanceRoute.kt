package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.util.ComposableLifecycle
import com.example.presentation.view.BalanceScreen
import com.example.presentation.viewmodel.BalanceViewModel

@Composable
fun BalanceRoute(
    modifier: Modifier = Modifier,
    viewModel: BalanceViewModel = hiltViewModel<BalanceViewModel>(),
    onAddTransactionCLick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()
    val isExchangeRateUpdated = rememberSaveable { mutableStateOf(false) }

    ComposableLifecycle(
        onStart = {
            if (!isExchangeRateUpdated.value) {
                viewModel.fetchExchangeRate()
                isExchangeRateUpdated.value = true
            }
        },
        onStop = {
            isExchangeRateUpdated.value = false
        }
    )

    BalanceScreen(
        modifier = modifier,
        uiState = uiState,
        transactions = transactions,
        onAddTransactionCLick = onAddTransactionCLick,
        onAddMoneyClick = viewModel::updateBalance
    )
}

