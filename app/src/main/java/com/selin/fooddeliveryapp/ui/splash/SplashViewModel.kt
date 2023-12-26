package com.selin.fooddeliveryapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel(){
    private val _liveSplashCompleted = MutableLiveData<Boolean>()
    val liveSplashCompleted: LiveData<Boolean> get() = _liveSplashCompleted

    fun startSplash() {
        viewModelScope.launch {
            delay(2000)
            _liveSplashCompleted.postValue(true)
        }
    }
}