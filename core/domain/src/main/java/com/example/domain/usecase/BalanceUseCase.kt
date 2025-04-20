package com.example.domain.usecase

import com.example.domain.model.BalanceModel
import com.example.domain.repository.BalanceRepository
import javax.inject.Inject

class BalanceUseCase @Inject constructor(
    private val balanceRepository: BalanceRepository
) {

    fun fetchBalance() = balanceRepository.getBalance()

    suspend fun updateBalance(balance: BalanceModel) {
        balanceRepository.updateBalance(balance)
    }

}