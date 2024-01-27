package com.selin.fooddeliveryapp

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
}