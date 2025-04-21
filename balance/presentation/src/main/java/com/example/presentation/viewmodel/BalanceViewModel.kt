package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.common.model.TransactionCategory
import com.example.common.model.TransactionType
import com.example.common.util.toBigDecimal
import com.example.designSystem.R
import com.example.domain.model.ExchangeRateModel
import com.example.domain.model.toBalanceModel
import com.example.domain.model.toTransactionModel
import com.example.domain.usecase.BalanceUseCase
import com.example.domain.usecase.TransactionsUseCase
import com.example.domain.usecases.ExchangeRateUseCase
import com.example.presentation.model.BalanceUiState
import com.example.presentation.model.Transaction
import com.example.presentation.model.addDateItem
import com.example.presentation.model.createBalanceUiState
import com.example.presentation.model.toTransaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val balanceUseCase: BalanceUseCase,
    private val exchangeRateUseCase: ExchangeRateUseCase,
    private val transactionsUseCase: TransactionsUseCase
) : ViewModel() {

    private val _exchangeRate = MutableStateFlow(ExchangeRateModel())
    internal val exchangeRate = _exchangeRate.asStateFlow()

    internal val balance = balanceUseCase.fetchBalance()

    internal val transactions: Flow<PagingData<Transaction>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE / 2
        ),
        pagingSourceFactory = { transactionsUseCase.getTransactions() }
    )
        .flow
        .map { pagingData ->
            pagingData.map { entity ->
                entity.toTransaction()
            }.insertSeparators { before, after ->
                addDateItem(before, after)
            }
        }
        .cachedIn(viewModelScope)

    internal val uiState = combine(
        balance,
        exchangeRate
    ) { balance, exchange ->
        createBalanceUiState(balance, exchange)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = BalanceUiState()
    )

    internal fun updateBalance(amount: String) {
        if (amount.isEmpty() || amount.toBigDecimal() == "0".toBigDecimal()) return
        viewModelScope.launch {
            val amountBigDecimal = amount.toBigDecimal()
            val sumBigDecimal = balance.first().amount.plus(amountBigDecimal)

            balanceUseCase.updateBalance(sumBigDecimal.toBalanceModel())
            transactionsUseCase.addTransaction(
                amount.toTransactionModel(
                    iconResId = R.drawable.ic_24_credit_card_down,
                    category = TransactionCategory.RechargeBalance,
                    transactionType = TransactionType.Recharge
                )
            )
        }
    }

    internal fun fetchExchangeRate() {
        viewModelScope.launch {
            val result = exchangeRateUseCase.fetchExchangeRate()
            _exchangeRate.update { result }
            delay(60 * 60 * 1000) // 1 hour
            fetchExchangeRate()
        }
    }

}
