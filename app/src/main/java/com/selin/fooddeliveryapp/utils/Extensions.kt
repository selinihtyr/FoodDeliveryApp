package com.selin.fooddeliveryapp.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.transitation(view: View, id: Int){
    findNavController(view).navigate(id)
}

fun Navigation.transitation(view: View, id: NavDirections){
    findNavController(view).navigate(id)
}