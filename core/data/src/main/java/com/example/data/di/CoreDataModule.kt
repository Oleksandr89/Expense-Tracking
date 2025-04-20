package com.example.data.di

import com.example.data.repository.TransactionsRepositoryImpl
import com.example.database.dao.BalanceDao
import com.example.database.dao.TransactionDao
import com.example.domain.repository.TransactionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreDataModule {

    @Provides
    @Singleton
    fun provideBalanceRepository(
        balanceDao: BalanceDao
    ): com.example.domain.repository.BalanceRepository {
        return com.example.data.repository.BalanceRepositoryImpl(balanceDao)
    }

    @Provides
    @Singleton
    fun provideTransactionsRepository(
        transactionDao: TransactionDao
    ): TransactionsRepository {
        return TransactionsRepositoryImpl(transactionDao)
    }

}