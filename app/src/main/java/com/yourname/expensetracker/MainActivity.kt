package com.yourname.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize our Data Layer
        val database = AppDatabase.getDatabase(this)
        val repository = ExpenseRepo(
            expenseDao = database.expenseDao()
        )

        setContent {
            ExpenseTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    
                    // 2. Set up the Navigation Controller
                    val navController = rememberNavController()
                    
                    // 3. Set up our ViewModels
                    val authViewModel: AuthViewModel = viewModel()
                    // Note: In a real app, use a ViewModel Provider Factory to pass the repository, 
                    // or use a Dependency Injection framework like Hilt.
                    val expenseViewModel = ExpenseViewModel(repository) 

                    // 4. The Navigation Map
                    NavHost(navController = navController, startDestination = "login") {
                        
                        // Route 1: The Login Screen
                        composable("login") {
                            LoginScreen(
                                onLoginClick = {
                                    authViewModel.simulateLogin()
                                    // Navigate to dashboard and remove login from the backstack 
                                    // so the 'back' button doesn't take them back to the login screen
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Route 2: The Main Dashboard
                        composable("dashboard") {
                            DashboardScreen(
                                expensesFlow = expenseViewModel.allExpenses,
                                totalExpensesFlow = expenseViewModel.totalExpenses,
                                onSyncClick = {
                                    // Pass the mock token to trigger our stubbed API fetch
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
