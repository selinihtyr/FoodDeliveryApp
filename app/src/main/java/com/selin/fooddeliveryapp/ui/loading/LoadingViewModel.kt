package com.selin.fooddeliveryapp.ui.loading

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingViewModel: ViewModel(){
    private val _liveSplashCompleted = MutableLiveData<Boolean>()
    val liveSplashCompleted: LiveData<Boolean> get() = _liveSplashCompleted

    fun startLoading() {
        viewModelScope.launch {
            delay(3000)
            _liveSplashCompleted.postValue(true)
        }
    }
}