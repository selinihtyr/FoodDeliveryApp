package com.selin.fooddeliveryapp.ui.map

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R

class MapViewModel : ViewModel() {

    fun createMarker(location: LatLng): MarkerOptions {
        return MarkerOptions().position(location).title("Metropol İstanbul")
    }

}