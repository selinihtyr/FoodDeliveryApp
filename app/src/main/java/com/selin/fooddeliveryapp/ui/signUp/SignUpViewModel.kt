package com.selin.fooddeliveryapp.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel(){
    private val auth = FirebaseAuth.getInstance()
    private val _navigateToSignInScreen = MutableSharedFlow<Unit>()
    val navigateToSignInScreen: SharedFlow<Unit> get() = _navigateToSignInScreen
    private val _error = MutableSharedFlow<SignUpError>()
    val error: SharedFlow<SignUpError> get() = _error

    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
    ) {

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            sendError(SignUpError.FILL_IN_THE_BLANKS)
            return
        }
        if (password.length < 6 || confirmPassword.length < 6) {
            sendError(SignUpError.MIN_PASSWORD_LENGTH)
            return
        }
        if (password != confirmPassword) {
            sendError(SignUpError.DIFFERENT_PASSWORD)
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sendError(SignUpError.INVALID_EMAIL_ADDRESS)
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToSignInScreen()
                } else {
                    sendError(SignUpError.EMAIL_ALREADY_IN_USE)
                }
            }
    }

    private fun sendError(error: SignUpError) {
        viewModelScope.launch {
            _error.emit(error)
        }
    }

    private fun navigateToSignInScreen() {
        viewModelScope.launch {
            _navigateToSignInScreen.emit(Unit)
        }
    }
}