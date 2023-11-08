package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var isCartSelected = false
    private var isHomeSelected = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observe()
    }

    private fun initViews() = with(binding) {
        tbCartMap.setOnClickListener {
            isCartSelected = !isCartSelected
            updateToggleButtonState(button = tbCartMap, isSelected = isCartSelected)
            findNavController().navigate(R.id.mapFragment_to_cartFragment)
        }

        tbHomeMap.setOnClickListener {
            isHomeSelected = !isHomeSelected
            updateToggleButtonState(button = tbHomeMap, isSelected = isHomeSelected)
            goBackToPreviousFragment()
        }
    }

    private fun observe() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        //40.9939749,29.0699236,14z
        val location = LatLng(40.9939749, 29.0699236)
        val zoomLevel = 14f
        mMap.addMarker(MarkerOptions().position(location).title("Metropol İstanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }

    private fun goBackToPreviousFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun updateToggleButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.tbutton_background else R.drawable.tbutton_background)
    }
}