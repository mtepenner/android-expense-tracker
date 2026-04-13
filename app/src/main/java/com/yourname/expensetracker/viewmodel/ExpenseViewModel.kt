package com.yourname.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yourname.expensetracker.data.local.Expense
import androidx.lifecycle.ViewModelProvider
import com.yourname.expensetracker.data.repository.ExpenseRepo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExpenseViewModelFactory(private val repository: ExpenseRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ExpenseViewModel(private val repository: ExpenseRepo) : ViewModel() {

    // Convert the cold Flow from Room into a hot StateFlow for Compose to observe.
    // The WhileSubscribed(5000) keeps the flow active for 5 seconds after the UI is hidden,
    // preventing unnecessary database re-queries during quick configuration changes.
    val allExpenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalExpenses: StateFlow<Double?> = repository.totalExpenses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun syncNewEmails(authToken: String) {
        // Launch a coroutine in the ViewModel scope. 
        // This ensures the network call finishes even if the user navigates away from the screen momentarily.
        viewModelScope.launch {
            try {
                repository.syncExpensesFromGmail(authToken)
            } catch (e: Exception) {
                // In a production app, update a StateFlow here to trigger an error Snackbar in the UI
                e.printStackTrace()
            }
        }
    }
}
