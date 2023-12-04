package com.selin.fooddeliveryapp.ui.end

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EndViewModel: ViewModel() {
    fun startAnimation(onComplete: () -> Unit) {
        viewModelScope.launch {
            onComplete.invoke()
        }

    }
}