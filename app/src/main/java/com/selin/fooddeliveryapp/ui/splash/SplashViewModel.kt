package com.selin.fooddeliveryapp.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel : ViewModel(){
    fun startTimer(onComplete: () -> Unit) {
        viewModelScope.launch {
            delay(2000)
            onComplete.invoke()
        }
    }
}