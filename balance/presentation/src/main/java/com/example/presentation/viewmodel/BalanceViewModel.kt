package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.common.util.formatDayMonth
import com.example.domain.model.Balance
import com.example.domain.model.ExchangeRate
import com.example.domain.model.Transaction
import com.example.domain.usecase.BalanceUseCase
import com.example.domain.usecase.TransactionsUseCase
import com.example.domain.usecases.ExchangeRateUseCase
import com.example.presentation.model.BalanceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val balanceUseCase: BalanceUseCase,
    private val exchangeRateUseCase: ExchangeRateUseCase,
    private val transactionsUseCase: TransactionsUseCase
) : ViewModel() {

    private val _exchangeRate = MutableStateFlow<ExchangeRate>(ExchangeRate(""))
    internal val exchangeRate = _exchangeRate.asStateFlow()

    private val _balance = MutableStateFlow<Balance>(Balance(0.toBigDecimal(), ""))
    internal val balance = _balance.asStateFlow()

    internal val transactions: Flow<PagingData<Transaction>> = Pager(
        config = PagingConfig(pageSize = 20, initialLoadSize = 20, prefetchDistance = 5),
        pagingSourceFactory = { transactionsUseCase.getTransactions() }
    )
        .flow
        .map { pagingData ->
            pagingData.map { entity ->
                Transaction(
                    timestamp = entity.timestamp,
                    amount = entity.amount,
                    category = entity.category,
                    transactionType = entity.transactionType
                )
            }.insertSeparators { before, after ->
                val beforeDate = before?.timestamp?.formatDayMonth()
                val afterDate = after?.timestamp?.formatDayMonth()

                if (beforeDate != afterDate) {
                    after?.copy(transactionType = "date")
                } else {
                    null
                }
            }
        }
        .cachedIn(viewModelScope)

    internal val uiState = combine(
        balanceUseCase.fetchBalance(),
        exchangeRate
    ) { balance, exchange ->
        _balance.update { balance }
        BalanceUiState("${balance.amount} BTC", exchange.exchangeRate)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = BalanceUiState("0", "BTC/USD  15,190.32")
    )

    internal fun updateBalance(amount: String) {
        if (amount.isEmpty()) return
        viewModelScope.launch {
            val sum = balance.value.amount.setScale(2, RoundingMode.HALF_UP)
                .plus(amount.toBigDecimal().setScale(2, RoundingMode.HALF_UP))
            balanceUseCase.updateBalance(sum.toPlainString())
            transactionsUseCase.addTransaction(
                Transaction(
                    timestamp = System.currentTimeMillis(),
                    amount = amount,
                    category = "recharge balance",
                    transactionType = "recharge"
                )
            )
        }
    }

    internal fun fetchExchangeRate() {
        viewModelScope.launch {
            val result = exchangeRateUseCase.fetchExchangeRate()
            _exchangeRate.update { result }
            delay(60 * 60 * 1000)
            fetchExchangeRate()
        }
    }

}
