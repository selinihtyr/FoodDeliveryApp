package com.selin.fooddeliveryapp.ui.shared

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    fun saveUserName(userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userName", userName)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("userName", null)
    }

    fun saveMail(mail: String) {
        val editor = sharedPreferences.edit()
        editor.putString("mail", mail)
        editor.apply()
    }

    fun getMail(): String? {
        return sharedPreferences.getString("mail", null)
    }
}