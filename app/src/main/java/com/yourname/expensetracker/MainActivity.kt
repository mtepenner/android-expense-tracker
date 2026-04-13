package com.yourname.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import com.yourname.expensetracker.api.EmailParser
import com.yourname.expensetracker.api.GmailClient
import com.yourname.expensetracker.data.local.AppDatabase
import com.yourname.expensetracker.data.repository.ExpenseRepo
import com.yourname.expensetracker.ui.screens.DashboardScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Manual Dependency Injection
        // In a larger app, you would use a library like Hilt or Dagger for this.
        val database = AppDatabase.getDatabase(this)
        val repository = ExpenseRepo(
            expenseDao = database.expenseDao(),
            gmailClient = GmailClient(),
            emailParser = EmailParser()
        )

        // 2. Set the UI Content
        setContent {
            MaterialTheme {
                Surface {
                    DashboardScreen(
                        expensesFlow = repository.allExpenses,
                        onSyncClick = {
                            lifecycleScope.launch {
                                // Trigger the sync process when the button is clicked
                                repository.syncExpensesFromGmail("mock_oauth_token")
                            }
                        }
                    )
                }
            }
        }
    }
}
