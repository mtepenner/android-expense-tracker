package com.yourname.expensetracker.data.repository

import com.yourname.expensetracker.api.EmailParser
import com.yourname.expensetracker.api.GmailClient
import com.yourname.expensetracker.data.local.Expense
import com.yourname.expensetracker.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow

class ExpenseRepo(
    private val expenseDao: ExpenseDao,
    private val gmailClient: GmailClient,
    private val emailParser: EmailParser
) {
    // Exposes the database to the UI as a continuous stream of data
    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()

    // Orchestrates fetching from API, parsing, and saving to DB
    suspend fun syncExpensesFromGmail(authToken: String) {
        val rawEmails = gmailClient.fetchRecentReceiptEmails(authToken)
        
        for (emailText in rawEmails) {
            val parsedExpense = emailParser.parseEmailToExpense(emailText)
            if (parsedExpense != null) {
                expenseDao.insertExpense(parsedExpense)
            }
        }
    }
}
