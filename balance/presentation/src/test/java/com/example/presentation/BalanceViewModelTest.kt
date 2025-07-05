package com.example.presentation

import com.example.domain.model.BalanceModel
import com.example.domain.model.ExchangeRateModel
import com.example.domain.usecase.BalanceUseCase
import com.example.domain.usecase.TransactionsUseCase
import com.example.domain.usecases.ExchangeRateUseCase
import com.example.presentation.viewmodel.BalanceViewModel
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class BalanceViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val balanceUseCase: BalanceUseCase = mockk(relaxed = true)
    private val exchangeRateUseCase: ExchangeRateUseCase = mockk(relaxed = true)
    private val transactionsUseCase: TransactionsUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BalanceViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { balanceUseCase.fetchBalance() } returns flowOf(
            BalanceModel(
                BigDecimal("100.00"),
                "100.00"
            )
        )
        coEvery { exchangeRateUseCase.fetchExchangeRate() } returns ExchangeRateModel()
        every { transactionsUseCase.getTransactions() } returns mockk()
        initViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = BalanceViewModel(balanceUseCase, exchangeRateUseCase, transactionsUseCase)
    }

    @Test
    fun `updateBalance should not update when amount is empty`() = runTest(testDispatcher) {
        // Clear any setup mocks
        clearMocks(balanceUseCase, transactionsUseCase, answers = false)
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit

        viewModel.updateBalance("")
        testScheduler.advanceUntilIdle()

        coVerify(exactly = 0) { balanceUseCase.updateBalance(any()) }
        coVerify(exactly = 0) { transactionsUseCase.addTransaction(any()) }
    }

    @Test
    fun `updateBalance should not update when amount is zero`() = runTest(testDispatcher) {
        // Clear any setup mocks
        clearMocks(balanceUseCase, transactionsUseCase, answers = false)
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit

        viewModel.updateBalance("0")
        testScheduler.advanceUntilIdle()

        coVerify(exactly = 0) { balanceUseCase.updateBalance(any()) }
        coVerify(exactly = 0) { transactionsUseCase.addTransaction(any()) }
    }

    @Test
    fun `updateBalance should update balance and add transaction when amount is valid`() =
        runTest(testDispatcher) {
            // Clear any setup mocks
            clearMocks(balanceUseCase, transactionsUseCase, answers = false)
            coEvery { balanceUseCase.updateBalance(any()) } returns Unit
            coEvery { transactionsUseCase.addTransaction(any()) } returns Unit

            viewModel.updateBalance("50.00")
            testScheduler.advanceUntilIdle()

            coVerify(exactly = 1) { balanceUseCase.updateBalance(any()) }
            coVerify(exactly = 1) { transactionsUseCase.addTransaction(any()) }
        }

}