package com.yourname.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yourname.expensetracker.data.local.Expense
import com.yourname.expensetracker.ui.components.ExpenseCard
import com.yourname.expensetracker.ui.components.SummaryChart
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    expensesFlow: Flow<List<Expense>>,
    totalExpensesFlow: Flow<Double?>,
    onSyncClick: () -> Unit
) {
    // Collecting states from the local database
    val expenses by expensesFlow.collectAsState(initial = emptyList())
    val totalAmount by totalExpensesFlow.collectAsState(initial = 0.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Expenses") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onSyncClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh, 
                            contentDescription = "Sync Emails",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Display the Summary Chart Component
            SummaryChart(totalAmount = totalAmount ?: 0.0)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Recent Transactions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.height(8.dp))

            // Display the list of Expense Cards
            if (expenses.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No expenses found. Tap the refresh icon to sync.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(expenses, key = { it.id }) { expense ->
                        ExpenseCard(expense)
                    }
                }
            }
        }
    }
}
