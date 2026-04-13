package com.yourname.expensetracker.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GmailClient {
    // In a production app, you will initialize the Google API Client here
    // using the OAuth token retrieved during user login.

    suspend fun fetchRecentReceiptEmails(token: String): List<String> = withContext(Dispatchers.IO) {
        // TODO: Query Gmail API for "subject:receipt OR subject:order"
        
        // Mock data for testing the architecture:
        listOf(
            "Thank you for your order at Amazon. Total: $45.99 on Oct 24.",
            "Your Netflix subscription of $15.49 has been renewed."
        )
    }
}
