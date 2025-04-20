package com.example.data.di

import com.example.data.api.BalanceService
import com.example.data.repository.ExchangeRateRepositoryImpl
import com.example.domain.repository.ExchangeRateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BalanceDataModule {

    @Provides
    @Singleton
    fun provideBalanceService(
        retrofit: Retrofit
    ): BalanceService {
        return retrofit.create(BalanceService::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangeRateRepository(
        service: BalanceService
    ): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(service)
    }

}