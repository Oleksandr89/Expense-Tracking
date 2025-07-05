package com.example.domain

import androidx.paging.PagingSource
import com.example.database.model.TransactionEntity
import com.example.domain.model.TransactionModel
import com.example.domain.repository.TransactionsRepository
import com.example.common.model.TransactionCategory
import com.example.common.model.TransactionType
import com.example.domain.usecase.TransactionsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TransactionsUseCaseTest {

    private val transactionsRepository: TransactionsRepository = mockk(relaxed = true)
    private val mockPagingSource: PagingSource<Int, TransactionEntity> = mockk(relaxed = true)
    private lateinit var transactionsUseCase: TransactionsUseCase

    @Before
    fun setup() {
        transactionsUseCase = TransactionsUseCase(transactionsRepository)
    }

    @Test
    fun `getTransactions should return paging source from repository`() {
        every { transactionsRepository.getTransactions() } returns mockPagingSource

        val result = transactionsUseCase.getTransactions()

        assert(result === mockPagingSource)
    }

    @Test
    fun `addTransaction should call repository addTransaction`() = runTest {
        val transaction = TransactionModel(
            timestamp = System.currentTimeMillis(),
            amount = "50.00",
            iconResId = 1,
            category = TransactionCategory.Groceries,
            transactionType = TransactionType.Withdraw
        )
        coEvery { transactionsRepository.addTransaction(transaction) } returns Unit

        transactionsUseCase.addTransaction(transaction)

        coVerify { transactionsRepository.addTransaction(transaction) }
    }
}