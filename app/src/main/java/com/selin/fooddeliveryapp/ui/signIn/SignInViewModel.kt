package com.selin.fooddeliveryapp.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    val navigateScreen = MutableSharedFlow<Unit>()
    private val _error = MutableSharedFlow<SignInError>()
    val error: SharedFlow<SignInError> get() = _error

    fun checkUserInfo() {
        val currentUser = auth.currentUser
        val isUserLoggedIn = currentUser != null
        if (isUserLoggedIn) {
            navigateToLoadingScreen()
        }
    }
    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            sendError(SignInError.FILL_IN_THE_BLANKS)
            return
        }
        if (password.length < 6) {
            sendError(SignInError.MIN_PASSWORD_LENGTH)
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sendError(SignInError.INVALID_EMAIL_ADDRESS)
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToLoadingScreen()
                } else {
                    sendError(SignInError.CHECK_INFORMATION)
                }
            }
    }
    private fun navigateToLoadingScreen() {
        viewModelScope.launch {
            navigateScreen.emit(Unit)
        }
    }

    private fun sendError(error: SignInError) {
        viewModelScope.launch {
            _error.emit(error)
        }
    }
}