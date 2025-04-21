package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.model.TransactionCategory
import com.example.common.model.TransactionType
import com.example.common.util.toBigDecimal
import com.example.designSystem.R
import com.example.domain.model.toBalanceModel
import com.example.domain.model.toTransactionModel
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
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val balanceUseCase: BalanceUseCase,
    private val transactionsUseCase: TransactionsUseCase
) : ViewModel() {

    private val balance = balanceUseCase.fetchBalance()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    internal val categories = _categories.asStateFlow()

    private val _uiState = MutableStateFlow(TransactionUiState())
    internal val uiState = _uiState.combine(categories) { uiState, categories ->
        TransactionUiState(
            navigateUp = uiState.navigateUp,
            isError = uiState.isError,
            errorTextResId = uiState.errorTextResId,
            categories = categories
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = TransactionUiState()
    )

    internal fun addTransaction(amount: String, category: TransactionCategory?) {
        viewModelScope.launch {
            if ((amount.isEmpty() || amount.toBigDecimal() == "0".toBigDecimal())  || category == null) {
                _uiState.update {
                    it.copy(
                        isError = true,
                        errorTextResId = R.string.choose_category_enter_amount
                    )
                }
                return@launch
            }

            val balanceBigDecimal = balance.first().amount
            val amountBigDecimal = amount.toBigDecimal()

            if (balanceBigDecimal >= amountBigDecimal) {
                transactionsUseCase.addTransaction(
                    amount.toTransactionModel(
                        category = category,
                        transactionType = TransactionType.Withdraw
                    )
                )

                val sumBigDecimal = balanceBigDecimal.minus(amountBigDecimal)
                balanceUseCase.updateBalance(sumBigDecimal.toBalanceModel())
                _uiState.update { it.copy(navigateUp = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isError = true,
                        errorTextResId = R.string.insufficient_funds
                    )
                }
            }
        }
    }

    init {
        _categories.update {
            listOf(
                Category(
                    type = TransactionCategory.Groceries,
                    iconResId = R.drawable.ic_24_shopping_cart,
                    categoryResId = R.string.groceries
                ),
                Category(
                    type = TransactionCategory.Taxi,
                    iconResId = R.drawable.ic_24_directions_car,
                    categoryResId = R.string.taxi
                ),
                Category(
                    type = TransactionCategory.Electronics,
                    iconResId = R.drawable.ic_24_monitor,
                    categoryResId = R.string.electronics
                ),
                Category(
                    type = TransactionCategory.Restaurant,
                    iconResId = R.drawable.ic_24_local_dining,
                    categoryResId = R.string.restaurant
                ),
                Category(
                    type = TransactionCategory.Other,
                    iconResId = R.drawable.ic_24_shopping_bag,
                    categoryResId = R.string.other
                )
            )
        }
    }

}