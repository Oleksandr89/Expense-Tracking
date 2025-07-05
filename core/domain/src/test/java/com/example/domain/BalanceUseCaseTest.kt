package com.example.domain

import com.example.domain.model.BalanceModel
import com.example.domain.repository.BalanceRepository
import com.example.domain.usecase.BalanceUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BalanceUseCaseTest {

    private val balanceRepository: BalanceRepository = mockk(relaxed = true)
    private lateinit var balanceUseCase: BalanceUseCase

    @Before
    fun setup() {
        balanceUseCase = BalanceUseCase(balanceRepository)
    }

    @Test
    fun `fetchBalance should return balance from repository`() {
        val expectedBalance = BalanceModel(amount = BigDecimal("100.00"), amountStr = "100.00")
        every { balanceRepository.getBalance() } returns flowOf(expectedBalance)

        val result = balanceUseCase.fetchBalance()

        assert(result === balanceRepository.getBalance())
    }

    @Test
    fun `updateBalance should call repository updateBalance`() = runTest {
        val balance = BalanceModel(amount = BigDecimal("250.00"), amountStr = "250.00")
        coEvery { balanceRepository.updateBalance(balance) } returns Unit

        balanceUseCase.updateBalance(balance)

        coVerify { balanceRepository.updateBalance(balance) }
    }
}