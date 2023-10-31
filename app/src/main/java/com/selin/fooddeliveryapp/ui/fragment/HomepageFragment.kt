package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.databinding.FragmentHomepageBinding
import com.selin.fooddeliveryapp.ui.adapter.HomepageAdapter
import com.selin.fooddeliveryapp.ui.viewModel.HomepageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageFragment : Fragment(), OnMapReadyCallback{
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var foodsAdapter: HomepageAdapter
    private var isCartSelected = false
    private var isHomeSelected = false
    private val viewModel: HomepageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeFilteredFoods()
        setupClickListeners()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvHomeCardDesign.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        foodsAdapter = HomepageAdapter(requireContext(), mutableListOf(), viewModel)
        binding.rvHomeCardDesign.adapter = foodsAdapter
    }

    private fun observeFilteredFoods() {
        viewModel.filteredFoods.observe(viewLifecycleOwner) { foods ->
            foodsAdapter.updateData(foods)
        }
    }

    private fun setupClickListeners() {
        binding.tbCart.setOnClickListener { toggleCartSelection() }
        binding.tbHome.setOnClickListener { toggleHomeSelection() }
        binding.ibMap.setOnClickListener { navigateToMapFragment() }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFoods(newText.orEmpty())
                return true
            }
        })
    }

    private fun toggleCartSelection() {
        isCartSelected = !isCartSelected
        updateToggleButtonState(binding.tbCart, isCartSelected)
        Navigation.findNavController(requireView()).navigate(R.id.cartFragment)
    }

    private fun toggleHomeSelection() {
        isHomeSelected = !isHomeSelected
        updateToggleButtonState(binding.tbHome, isHomeSelected)
        Navigation.findNavController(requireView()).navigate(R.id.homepageFragment)
    }

    private fun navigateToMapFragment() {
        Navigation.findNavController(requireView()).navigate(R.id.mapFragment)
    }

    private fun updateToggleButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.tbutton_background else R.drawable.tbutton_background)
    }

    override fun onMapReady(p0: GoogleMap) {
        val mMap = p0
        val location = LatLng(40.9939749, 29.0699236)
        val zoomLevel = 14f
        mMap.addMarker(MarkerOptions().position(location).title("Metropol İstanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }
}