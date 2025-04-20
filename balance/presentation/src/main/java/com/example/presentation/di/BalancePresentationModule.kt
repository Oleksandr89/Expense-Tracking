package com.example.presentation.di

import androidx.lifecycle.ViewModel
import com.example.presentation.viewmodel.BalanceViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BalancePresentationModule {

    @Binds
    @Singleton
    abstract fun provideViewModel(viewModel: BalanceViewModel): ViewModel

}