package com.example.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
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

    ComposableLifecycle(
        onStart = {
            viewModel.fetchExchangeRate()
        }
    )


    BalanceScreen(
        modifier = modifier,
        uiState = uiState,
        transactions = transactions,
        onAddTransactionCLick = onAddTransactionCLick,
        onAddMoneyCLick = viewModel::updateBalance
    )
}

@Composable
fun ComposableLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: ((LifecycleOwner) -> Unit)? = null,
    onStop: ((LifecycleOwner) -> Unit)? = null,
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_START -> onStart?.invoke(source)
                Lifecycle.Event.ON_STOP -> onStop?.invoke(source)
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
