package com.example.data.repository

import androidx.paging.PagingSource
import com.example.database.dao.TransactionDao
import com.example.database.model.TransactionEntity
import com.example.domain.model.TransactionModel
import com.example.domain.model.toTransactionEntity
import com.example.domain.repository.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionsRepository {

    override fun getTransactions(): PagingSource<Int, TransactionEntity> {
        return transactionDao.getTransactions()
    }

    override suspend fun addTransaction(transaction: TransactionModel) {
        withContext(Dispatchers.IO) {
            transactionDao.addTransaction(transaction.toTransactionEntity())
        }
    }
}