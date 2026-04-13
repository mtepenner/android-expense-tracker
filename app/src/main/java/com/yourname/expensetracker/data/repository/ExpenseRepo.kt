package com.yourname.expensetracker.data.repository

import com.yourname.expensetracker.api.EmailParser
import com.yourname.expensetracker.api.GmailClient
import com.yourname.expensetracker.data.local.Expense
import com.yourname.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepo(
    private val expenseDao: ExpenseDao,
    private val gmailClient: GmailClient = GmailClient(),
    private val emailParser: EmailParser = EmailParser()
) {

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

    // NEW: Added missing function called by ExpenseViewModel
    suspend fun syncExpensesFromGmail(authToken: String) {
        // 1. Fetch raw emails using the API Client
        val emails = gmailClient.fetchRecentReceiptEmails(authToken)
        
        // 2. Parse the emails into Expense objects
        val parsedExpenses = emails.mapNotNull { emailBody ->
            emailParser.parseEmailToExpense(emailBody)
        }
        
        // 3. Save to local Room database
        if (parsedExpenses.isNotEmpty()) {
            saveMultipleExpenses(parsedExpenses)
        }
    }
}
