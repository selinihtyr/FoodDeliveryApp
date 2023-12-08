package com.selin.fooddeliveryapp.ui.signIn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()
    private var navigateToHomepageCallback: (() -> Unit)? = null
    private val _message = MutableSharedFlow<String>() // Changed to String
    val message: SharedFlow<String> get() = _message

    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        val context = getApplication<Application>().applicationContext

        if (email.isEmpty() || password.isEmpty()) {
            sendMessage(context.getString(R.string.fill_blanks_text))
            callback(false)
            return
        }
        if (password.length < 6) {
            sendMessage(context.getString(R.string.password_alert))
            callback(false)
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sendMessage(context.getString(R.string.ınvalid_alert))
            callback(false)
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    callback(true)
                } else {
                    sendMessage(context.getString(R.string.verification_email_error))
                    callback(false)
                }
            }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _message.emit(message)
        }
    }

    fun setNavigateToHomepageCallback(callback: () -> Unit) {
        navigateToHomepageCallback = callback
    }

    fun checkUserInfo() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToHomepageCallback?.invoke()
        }
    }
}