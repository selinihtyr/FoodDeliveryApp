package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentMapBinding

class MapFragment : Fragment() , OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.tbCart4.setOnClickListener {
            isCartSelected = !isCartSelected
            updateToggleButtonState()
            Navigation.findNavController(it).navigate(R.id.mapFragment_to_cartFragment)
        }

        binding.tbHome4.setOnClickListener {
            isHomeSelected = !isHomeSelected
            updateToggleButtonState()
            goBackToPreviousFragment()
        }
        return binding.root
    }
    private var isCartSelected = false
    private var isHomeSelected = false

    private fun updateToggleButtonState() {
        if (isCartSelected) {
            binding.tbCart4.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbCart4.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }

        if (isHomeSelected) {
            binding.tbHome4.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbHome4.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }
    }

    private fun goBackToPreviousFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.popBackStack()
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        //40.9939749,29.0699236,14z
        val location = LatLng(40.9939749,29.0699236)
        val zoomLevel = 14f
        mMap.addMarker(MarkerOptions().position(location).title("Metropol İstanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }
}