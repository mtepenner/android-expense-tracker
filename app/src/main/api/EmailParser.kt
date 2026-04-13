package com.yourname.expensetracker.api

import com.yourname.expensetracker.data.local.Expense

class EmailParser {
    
    fun parseEmailToExpense(emailBody: String): Expense? {
        // A very basic Regex to find dollar amounts for demonstration.
        // You will likely need more robust NLP or specific vendor Regex rules here.
        val amountRegex = "\\$([0-9]+\\.[0-9]{2})".toRegex()
        val match = amountRegex.find(emailBody)
        
        return if (match != null) {
            val amount = match.groupValues[1].toDouble()
            
            // Hardcoded vendor/category for the stub
            Expense(
                vendor = "Parsed Vendor", 
                amount = amount, 
                category = "General", 
                date = System.currentTimeMillis()
            )
        } else {
            null // Could not find an expense in this email
        }
    }
}
