package com.example.database.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.database.ExpenseTrackingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesExpenseTrackingDatabase(
        @ApplicationContext appContext: Context,
    ): ExpenseTrackingDatabase = Room.databaseBuilder(
        appContext,
        ExpenseTrackingDatabase::class.java,
        "expense_tracking_database",
    ).setQueryCallback({ query, args ->
        Log.d("ROOM_SQL", "Query: $query | Args: $args")
    }, Executors.newSingleThreadExecutor())
        .build()

}