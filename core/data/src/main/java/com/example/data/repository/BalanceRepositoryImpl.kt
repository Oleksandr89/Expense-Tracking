package com.example.data.repository

import com.example.database.dao.BalanceDao
import com.example.database.model.BalanceEntity
import com.example.domain.model.Balance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao
) : com.example.domain.repository.BalanceRepository {

    override fun getBalance(): Flow<Balance> {
        return balanceDao.getBalance().map {
            Balance(
                amount = it?.amount?.toBigDecimal() ?: 0.toBigDecimal(),
                amountStr = it?.amount.orEmpty()
            )
        }
    }

    override suspend fun updateBalance(amount: String) = withContext(Dispatchers.IO) {
        balanceDao.updateBalance(BalanceEntity(amount = amount))
    }

}