package com.selin.fooddeliveryapp.ui.map

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R

class MapViewModel : ViewModel() {
    var isCartSelected = false
        private set

    var isHomeSelected = false
        private set

    fun toggleCartSelected() {
        isCartSelected = !isCartSelected
    }

    fun toggleHomeSelected() {
        isHomeSelected = !isHomeSelected
    }

    fun createMarker(location: LatLng): MarkerOptions {
        return MarkerOptions().position(location).title("Metropol İstanbul")
    }

    fun updateToggleButtonState(button: Button, isSelected: Boolean) {
        val backgroundResource =
            if (isSelected) R.drawable.tbutton_background else R.drawable.tbutton_background
        button.setBackgroundResource(backgroundResource)
    }
}