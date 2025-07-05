package com.example.data

import com.example.data.repository.BalanceRepositoryImpl
import com.example.database.dao.BalanceDao
import com.example.database.model.BalanceEntity
import com.example.domain.model.BalanceModel
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.math.BigDecimal

class BalanceRepositoryImplTest {

    private val balanceDao: BalanceDao = mockk(relaxed = true)
    private lateinit var repository: BalanceRepositoryImpl

    @Before
    fun setup() {
        repository = BalanceRepositoryImpl(balanceDao)
    }

    @Test
    fun `getBalance should return balance model from dao`() = runTest {
        val balanceEntity = BalanceEntity(amountStr = "100.50")
        every { balanceDao.getBalance() } returns flowOf(balanceEntity)

        val result = repository.getBalance().first()

        assertEquals(BigDecimal("100.50"), result.amount)
    }

    @Test
    fun `updateBalance should call dao updateBalance`() = runTest {
        val balanceModel = BalanceModel(amount = BigDecimal("200.00"), amountStr = "200.00")
        coEvery { balanceDao.updateBalance(any()) } returns Unit

        repository.updateBalance(balanceModel)

        coVerify { balanceDao.updateBalance(any()) }
    }
}