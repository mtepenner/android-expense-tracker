package com.yourname.expensetracker.data.repository

import com.yourname.expensetracker.data.local.Expense
import com.yourname.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepo(private val expenseDao: ExpenseDao) {

    // Room executes all queries that return Flow on a background thread safely,
    // so we don't need to specify Dispatchers.IO here.
    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()
    
    val totalExpenses: Flow<Double?> = expenseDao.getTotalExpenses()

    // Suspend function forces this to be called from a Coroutine, keeping the main UI thread unblocked.
    suspend fun saveExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun saveMultipleExpenses(expenses: List<Expense>) {
        expenseDao.insertAll(expenses)
    }
}
