package com.selin.fooddeliveryapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.ui.signIn.SignInError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
class SplashViewModel (
) : ViewModel() {
    private val _liveSplashCompleted = MutableLiveData<Boolean>()
    val liveSplashCompleted: LiveData<Boolean> get() = _liveSplashCompleted
    private val _navigateScreen = MutableSharedFlow<Unit>()
    private val auth = FirebaseAuth.getInstance()
    private var isUserSignedIn = false

    fun checkUserInfo() {
        val currentUser = auth.currentUser
        isUserSignedIn = currentUser != null
        if (isUserSignedIn) {
            navigateToLoadingScreen()
        }
    }

    private fun navigateToLoadingScreen() {
        viewModelScope.launch {
            _navigateScreen.emit(Unit)
        }
    }

    fun startSplash() {
        viewModelScope.launch {
            delay(2000)
            _liveSplashCompleted.postValue(true)
        }
    }

    fun navigateToDestination(onBoardingFinished: Boolean): Int {
        return if (isUserSignedIn) {
            R.id.homepageFragment
        } else {
            if (onBoardingFinished) {
                R.id.signInFragment
            } else {
                R.id.splash_to_onBoardingFragment
            }
        }
    }
}