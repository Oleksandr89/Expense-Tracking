package com.example.domain.repository

import com.example.domain.model.BalanceModel
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun getBalance(): Flow<BalanceModel>
    suspend fun updateBalance(balance: BalanceModel)
}