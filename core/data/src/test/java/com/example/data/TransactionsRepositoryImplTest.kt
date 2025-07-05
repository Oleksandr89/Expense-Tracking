package com.example.data

import androidx.paging.PagingSource
import com.example.database.dao.TransactionDao
import com.example.database.model.TransactionEntity
import com.example.domain.model.TransactionModel
import com.example.common.model.TransactionCategory
import com.example.common.model.TransactionType
import com.example.data.repository.TransactionsRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TransactionsRepositoryImplTest {

    private val transactionDao: TransactionDao = mockk(relaxed = true)
    private val mockPagingSource: PagingSource<Int, TransactionEntity> = mockk(relaxed = true)
    private lateinit var repository: TransactionsRepositoryImpl

    @Before
    fun setup() {
        repository = TransactionsRepositoryImpl(transactionDao)
    }

    @Test
    fun `getTransactions should return paging source from dao`() {
        every { transactionDao.getTransactions() } returns mockPagingSource

        val result = repository.getTransactions()

        assert(result === mockPagingSource)
    }

    @Test
    fun `addTransaction should call dao addTransaction`() = runTest {
        val transaction = TransactionModel(
            timestamp = System.currentTimeMillis(),
            amount = "50.00",
            iconResId = 1,
            category = TransactionCategory.Groceries,
            transactionType = TransactionType.Withdraw
        )
        coEvery { transactionDao.addTransaction(any()) } returns Unit

        repository.addTransaction(transaction)

        coVerify { transactionDao.addTransaction(any()) }
    }
}