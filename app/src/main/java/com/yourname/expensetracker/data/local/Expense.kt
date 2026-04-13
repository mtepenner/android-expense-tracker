package com.yourname.expensetracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vendor: String,
    val amount: Double,
    val category: String,
    val date: Long // Stored as a Unix timestamp for easier sorting
)
