package com.selin.fooddeliveryapp.ui.end

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EndViewModel: ViewModel() {
    private val _endCompleted = MutableLiveData<Boolean>()
    val endCompleted: LiveData<Boolean> get() = _endCompleted

    fun startSplash() {
        viewModelScope.launch {
            delay(2000)
            _endCompleted.postValue(true)
        }
    }
}