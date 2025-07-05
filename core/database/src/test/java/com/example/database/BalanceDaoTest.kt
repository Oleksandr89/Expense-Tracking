package com.example.database

import com.example.database.dao.BalanceDao
import com.example.database.dao.TransactionDao
import com.example.database.model.BalanceEntity
import com.example.database.model.TransactionEntity
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class BalanceDaoTest {

    @Test
    fun `getBalance should return flow of balance entity`() = runTest {
        val mockDao = mockk<BalanceDao>()
        val balanceEntity = BalanceEntity(id = 1, amountStr = "100.00")
        
        every { mockDao.getBalance() } returns flowOf(balanceEntity)
        
        val result = mockDao.getBalance()
        
        verify { mockDao.getBalance() }
    }

    @Test
    fun `updateBalance should be suspended function`() = runTest {
        val mockDao = mockk<BalanceDao>()
        val balanceEntity = BalanceEntity(id = 1, amountStr = "150.00")
        
        coEvery { mockDao.updateBalance(balanceEntity) } returns Unit
        
        mockDao.updateBalance(balanceEntity)
        
        coVerify { mockDao.updateBalance(balanceEntity) }
    }
}

class TransactionDaoTest {

    @Test
    fun `getTransactions should return paging source`() {
        val mockDao = mockk<TransactionDao>()
        val mockPagingSource = mockk<androidx.paging.PagingSource<Int, TransactionEntity>>()
        
        every { mockDao.getTransactions() } returns mockPagingSource
        
        val result = mockDao.getTransactions()
        
        assert(result === mockPagingSource)
        verify { mockDao.getTransactions() }
    }

    @Test
    fun `addTransaction should be suspended function`() = runTest {
        val mockDao = mockk<TransactionDao>()
        val transactionEntity = TransactionEntity(
            timestamp = System.currentTimeMillis(),
            amount = "25.00",
            iconResId = 1,
            category = "Groceries",
            transactionType = "Withdraw"
        )
        
        coEvery { mockDao.addTransaction(transactionEntity) } returns Unit
        
        mockDao.addTransaction(transactionEntity)
        
        coVerify { mockDao.addTransaction(transactionEntity) }
    }
}