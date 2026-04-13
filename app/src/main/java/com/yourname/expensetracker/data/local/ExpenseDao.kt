package com.yourname.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    // If an expense with the same ID exists, replace it
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenses: List<Expense>)

    // Returns a continuous stream of data ordered by newest first
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    // Useful for a dashboard summary chart
    @Query("SELECT SUM(amount) FROM expenses")
    fun getTotalExpenses(): Flow<Double?>
}
