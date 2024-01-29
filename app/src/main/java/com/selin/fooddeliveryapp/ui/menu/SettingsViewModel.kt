package com.selin.fooddeliveryapp.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _passwordChangeResult = MutableStateFlow<String?>(null)
    val passwordChangeResult: StateFlow<String?> = _passwordChangeResult
    private val _message = MutableSharedFlow<String>()

    fun changePassword(oldPassword: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(user?.email!!, oldPassword)
        viewModelScope.launch {
            user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { updatePasswordTask ->
                            if (updatePasswordTask.isSuccessful) {
                                _passwordChangeResult.value = "Password changed successfully"
                            } else {
                                _passwordChangeResult.value = "Failed to change password"
                            }
                        }
                } else {
                    _passwordChangeResult.value = "Invalid old password"
                }
            }
        }
    }

    fun showMessage(message: String) {
        viewModelScope.launch {
            _message.emit(message)
        }
    }
}