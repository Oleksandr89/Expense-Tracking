package com.example.database.di

import com.example.database.ExpenseTrackingDatabase
import com.example.database.dao.BalanceDao
import com.example.database.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaosModule {

    @Provides
    fun providesBalanceDao(
        database: ExpenseTrackingDatabase,
    ): BalanceDao = database.balanceDao()

    @Provides
    fun providesTransactionDao(
        database: ExpenseTrackingDatabase,
    ): TransactionDao = database.transactionDao()

}