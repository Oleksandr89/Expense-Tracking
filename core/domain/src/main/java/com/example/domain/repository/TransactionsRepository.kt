package com.example.domain.repository

import androidx.paging.PagingSource
import com.example.database.model.TransactionEntity
import com.example.domain.model.Transaction

interface TransactionsRepository {
    fun getTransactions(): PagingSource<Int, TransactionEntity>
    suspend fun addTransaction(transaction: Transaction)
}