package com.selin.fooddeliveryapp.ui.signUp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()
    private val _message = MutableSharedFlow<String>()
    val message: SharedFlow<String> get() = _message

    fun signUp(
        email: String,
        password: String,
        confirmPassword: String,
        callback: (Boolean) -> Unit
    ) {
        val context = getApplication<Application>().applicationContext

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            sendMessage(context.getString(R.string.fill_blanks_text))
            callback(false)
            return
        }
        if (password.length < 6 || confirmPassword.length < 6) {
            sendMessage(context.getString(R.string.password_alert))
            callback(false)
            return
        }
        if (password != confirmPassword) {
            sendMessage(context.getString(R.string.password_is_not_the_same))
            callback(false)
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            sendMessage(context.getString(R.string.ınvalid_alert))
            callback(false)
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    sendMessage(context.getString(R.string.this_mail_is_in_use))
                    callback(false)
                }
            }
    }

    private fun sendMessage(message: String) {
        viewModelScope.launch {
            _message.emit(message)
        }
    }
}