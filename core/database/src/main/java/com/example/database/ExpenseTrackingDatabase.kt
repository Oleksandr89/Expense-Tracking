package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.BalanceDao
import com.example.database.dao.TransactionDao
import com.example.database.model.BalanceEntity
import com.example.database.model.TransactionEntity

@Database(
    entities = [
        BalanceEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = true,
)

abstract class ExpenseTrackingDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
    abstract fun transactionDao(): TransactionDao
}