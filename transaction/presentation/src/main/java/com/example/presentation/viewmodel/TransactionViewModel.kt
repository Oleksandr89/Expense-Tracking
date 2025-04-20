package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.designSystem.R
import com.example.domain.model.Transaction
import com.example.domain.usecase.BalanceUseCase
import com.example.domain.usecase.TransactionsUseCase
import com.example.presentation.model.Category
import com.example.presentation.model.TransactionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val balanceUseCase: BalanceUseCase,
    private val transactionsUseCase: TransactionsUseCase
) : ViewModel() {

    private val balance = balanceUseCase.fetchBalance()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    internal val categories = _categories.asStateFlow()

    private val _uiState = MutableStateFlow<TransactionUiState>(TransactionUiState())
    internal val uiState = _uiState.combine(categories) { uiState, categories ->
        TransactionUiState(
            navigateUp = uiState.navigateUp,
            isError = uiState.isError,
            categories = categories
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TransactionUiState()
    )

    init {
        _categories.update {
            listOf(
                Category(
                    id = R.string.groceries,
                    iconResId = R.drawable.ic_24_shopping_cart,
                    categoryResId = R.string.groceries
                ),
                Category(
                    id = R.string.taxi,
                    iconResId = R.drawable.ic_24_directions_car,
                    categoryResId = R.string.taxi
                ),
                Category(
                    id = R.string.electronics,
                    iconResId = R.drawable.ic_24_monitor,
                    categoryResId = R.string.electronics
                ),
                Category(
                    id = R.string.restaurant,
                    iconResId = R.drawable.ic_24_local_dining,
                    categoryResId = R.string.restaurant
                ),
                Category(
                    id = R.string.other,
                    iconResId = R.drawable.ic_24_shopping_bag,
                    categoryResId = R.string.other
                )
            )
        }
    }

    internal fun addTransaction(amount: String, category: String) {
        viewModelScope.launch {
            if (amount.isEmpty() || category.isEmpty()) {
                _uiState.update { it.copy(isError = true) }
                return@launch
            }

            val balance = balance.first().amount
            val amountBigDecimal = amount.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
            if (balance >= amountBigDecimal) {
                transactionsUseCase.addTransaction(
                    Transaction(
                        timestamp = System.currentTimeMillis(),
                        amount = amount,
                        category = category,
                        transactionType = "withdraw"
                    )
                )
                balanceUseCase.updateBalance(balance.minus(amountBigDecimal).toPlainString())
                _uiState.update { it.copy(navigateUp = true) }
            } else {

            }
        }
    }

}