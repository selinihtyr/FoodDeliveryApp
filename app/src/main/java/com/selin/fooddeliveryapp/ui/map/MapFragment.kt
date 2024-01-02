package com.selin.fooddeliveryapp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {
    private val viewModel: MapViewModel by lazy { MapViewModel() }
    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeMap()
    }

    private fun initViews() {
        with(binding) {
            tbCartMap.setOnClickListener {
                viewModel.toggleCartSelected()
                viewModel.updateToggleButtonState(tbCartMap, viewModel.isCartSelected)
                findNavController().navigate(R.id.mapFragment_to_cartFragment)
            }

            tbHomeMap.setOnClickListener {
                viewModel.toggleHomeSelected()
                viewModel.updateToggleButtonState(tbHomeMap, viewModel.isHomeSelected)
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun observeMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = LatLng(40.9939749, 29.0699236)
        val zoomLevel = 14f

        mMap.addMarker(viewModel.createMarker(location))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }
}