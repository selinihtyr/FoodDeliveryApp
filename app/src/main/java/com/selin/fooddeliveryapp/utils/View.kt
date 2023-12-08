package com.selin.fooddeliveryapp.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.selin.fooddeliveryapp.R

fun View.snackbar(message: String) {
    Snackbar.make(this, message, 1200)
        .setTextColor(Color.WHITE)
        .setBackgroundTint(Color.TRANSPARENT)
        .show()
}