package com.selin.fooddeliveryapp.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selin.fooddeliveryapp.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor (application: Application) : ViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData()
    private val sharedPrefHelper = SharedPrefHelper(application)

    fun saveUserName(userName: String) {
        sharedPrefHelper.saveUserName(userName)
        this.userName.value = userName
    }

    fun loadUserName() {
        val userName = sharedPrefHelper.getUserName()
        if (userName != null) {
            this.userName.value = userName
        }
    }
}