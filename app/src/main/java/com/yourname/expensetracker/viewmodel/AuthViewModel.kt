package com.yourname.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Represents the different states the user's authentication can be in
sealed class AuthState {
    object LoggedOut : AuthState()
    object Loading : AuthState()
    data class LoggedIn(val authToken: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    // Internal mutable state (only the ViewModel can change this)
    private val _authState = MutableStateFlow<AuthState>(AuthState.LoggedOut)
    
    // Public immutable state (the UI observes this)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun simulateLogin() {
        _authState.value = AuthState.Loading
        
        // In a real app, this is where you would process the Google Sign-In intent result.
        // For now, we simulate a successful login and return a mock token.
        val mockToken = "ya29.mock_oauth_token_from_google"
        _authState.value = AuthState.LoggedIn(mockToken)
    }

    fun logout() {
        _authState.value = AuthState.LoggedOut
    }
}
