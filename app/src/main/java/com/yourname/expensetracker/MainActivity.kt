package com.yourname.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourname.expensetracker.data.local.AppDatabase
import com.yourname.expensetracker.data.repository.ExpenseRepo
import com.yourname.expensetracker.ui.screens.DashboardScreen
import com.yourname.expensetracker.ui.screens.LoginScreen
import com.yourname.expensetracker.ui.theme.ExpenseTrackerTheme
import com.yourname.expensetracker.viewmodel.AuthViewModel
import com.yourname.expensetracker.viewmodel.ExpenseViewModel

// 1. Define the Factory to handle ViewModel injection
class ExpenseViewModelFactory(private val repository: ExpenseRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(this)
        val repository = ExpenseRepo(
            expenseDao = database.expenseDao()
        )

        setContent {
            ExpenseTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()

                    // 2. FIX: Use the Factory to instantiate ExpenseViewModel
                    val expenseViewModel: ExpenseViewModel = viewModel(
                        factory = ExpenseViewModelFactory(repository)
                    )

                    NavHost(navController = navController, startDestination = "login") {
                        
                        composable("login") {
                            LoginScreen(
                                onLoginClick = {
                                    authViewModel.simulateLogin()
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("dashboard") {
                            DashboardScreen(
                                expensesFlow = expenseViewModel.allExpenses,
                                totalExpensesFlow = expenseViewModel.totalExpenses,
                                onSyncClick = {
                                    expenseViewModel.syncNewEmails("mock_oauth_token")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
