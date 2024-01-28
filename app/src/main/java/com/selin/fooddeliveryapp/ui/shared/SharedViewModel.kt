package com.selin.fooddeliveryapp.ui.shared

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor (application: Application) : ViewModel() {
    val userName: MutableLiveData<String> = MutableLiveData()
    val mail: MutableLiveData<String> = MutableLiveData()
    val photo: MutableLiveData<String> = MutableLiveData()
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

    fun saveMail(mail: String) {
        sharedPrefHelper.saveMail(mail)
    }

    fun loadMail() {
        val mail = sharedPrefHelper.getMail()
        if (mail != null) {
            this.mail.value = mail
        }
    }

    fun savePhoto(photo: String) {
        sharedPrefHelper.savePhoto(photo)
    }

    fun loadPhoto() {
        val photo = sharedPrefHelper.getPhoto()
        if (photo != null) {
            this.photo.value = photo
        }
    }
}