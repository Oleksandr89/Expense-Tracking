package com.example.domain.repository

import com.example.domain.model.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceRepository {
    fun getBalance(): Flow<Balance>
    suspend fun updateBalance(amount: String)
}