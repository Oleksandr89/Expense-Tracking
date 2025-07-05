package com.example.presentation

import com.example.common.model.TransactionCategory
import com.example.domain.model.BalanceModel
import com.example.domain.usecase.BalanceUseCase
import com.example.domain.usecase.TransactionsUseCase
import com.example.presentation.model.Category
import com.example.presentation.viewmodel.TransactionViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class TransactionViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val balanceUseCase: BalanceUseCase = mockk(relaxed = true)
    private val transactionsUseCase: TransactionsUseCase = mockk(relaxed = true)
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { balanceUseCase.fetchBalance() } returns flowOf(
            BalanceModel(
                BigDecimal("100.00"),
                "100.00"
            )
        )
        initViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = TransactionViewModel(balanceUseCase, transactionsUseCase)
    }

    @Test
    fun `addTransaction should show error when amount is empty`() = runTest {
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit

        val category = Category(TransactionCategory.Groceries, 1, 1)
        viewModel.addTransaction("", category)

        val uiState = viewModel.uiState.first()
        assert(uiState.isError)
    }

    @Test
    fun `addTransaction should show error when amount is zero`() = runTest {
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit

        val category = Category(TransactionCategory.Groceries, 1, 1)
        viewModel.addTransaction("0", category)

        val uiState = viewModel.uiState.first()
        assert(uiState.isError)
    }

    @Test
    fun `addTransaction should show error when category is null`() = runTest {
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit

        viewModel.addTransaction("50.00", null)

        val uiState = viewModel.uiState.first()
        assert(uiState.isError)
    }

    @Test
    fun `addTransaction should show insufficient funds error when balance is insufficient`() =
        runTest {
            // Create a new ViewModel with insufficient balance
            val insufficientBalanceUseCase = mockk<BalanceUseCase>()
            every { insufficientBalanceUseCase.fetchBalance() } returns flowOf(
                BalanceModel(
                    BigDecimal("30.00"),
                    "30.00"
                )
            )
            coEvery { transactionsUseCase.addTransaction(any()) } returns Unit
            coEvery { balanceUseCase.updateBalance(any()) } returns Unit

            val testViewModel =
                TransactionViewModel(insufficientBalanceUseCase, transactionsUseCase)

            val category = Category(TransactionCategory.Groceries, 1, 1)
            testViewModel.addTransaction("50.00", category)

            val uiState = testViewModel.uiState.first()
            assert(uiState.isError)
        }

    @Test
    fun `addTransaction should succeed when balance is sufficient`() = runTest {
        coEvery { transactionsUseCase.addTransaction(any()) } returns Unit
        coEvery { balanceUseCase.updateBalance(any()) } returns Unit

        val category = Category(TransactionCategory.Groceries, 1, 1)
        viewModel.addTransaction("50.00", category)

        coVerify { transactionsUseCase.addTransaction(any()) }
        coVerify { balanceUseCase.updateBalance(any()) }

        val uiState = viewModel.uiState.first()
        assert(uiState.navigateUp)
    }

    @Test
    fun `categories should be initialized with default values`() = runTest {
        val categories = viewModel.categories.first()

        assert(categories.isNotEmpty())
        assert(categories.any { it.type == TransactionCategory.Groceries })
        assert(categories.any { it.type == TransactionCategory.Taxi })
        assert(categories.any { it.type == TransactionCategory.Electronics })
        assert(categories.any { it.type == TransactionCategory.Restaurant })
        assert(categories.any { it.type == TransactionCategory.Other })
    }
}