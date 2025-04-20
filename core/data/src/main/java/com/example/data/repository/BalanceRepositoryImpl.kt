package com.example.data.repository

import com.example.database.dao.BalanceDao
import com.example.domain.model.BalanceModel
import com.example.domain.model.toBalanceEntity
import com.example.domain.model.toBalanceModel
import com.example.domain.repository.BalanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    private val balanceDao: BalanceDao
) : BalanceRepository {

    override fun getBalance(): Flow<BalanceModel> {
        return balanceDao.getBalance().map { balanceEntity ->
            balanceEntity.toBalanceModel()
        }
    }

    override suspend fun updateBalance(balance: BalanceModel) = withContext(Dispatchers.IO) {
        balanceDao.updateBalance(balance.toBalanceEntity())
    }

}