package com.example.domain.usecase

import androidx.paging.PagingSource
import com.example.database.model.TransactionEntity
import com.example.domain.model.TransactionModel
import com.example.domain.repository.TransactionsRepository
import javax.inject.Inject

class TransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    fun getTransactions(): PagingSource<Int, TransactionEntity> =
        transactionsRepository.getTransactions()

    suspend fun addTransaction(transaction: TransactionModel) =
        transactionsRepository.addTransaction(transaction)

}