package com.example.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Query("SELECT * FROM balance WHERE id = 1")
    fun getBalance(): Flow<BalanceEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBalance(balance: BalanceEntity)

}