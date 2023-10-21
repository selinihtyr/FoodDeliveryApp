package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.databinding.FragmentHomepageBinding
import com.selin.fooddeliveryapp.ui.adapter.HomepageAdapter
import com.selin.fooddeliveryapp.ui.viewModel.HomepageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomepageFragment : Fragment(){
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: HomepageViewModel
    private lateinit var foodsAdapter: HomepageAdapter
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        binding.rvHomeCardDesign.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

        viewModel.filteredFoods.observe(viewLifecycleOwner) { foods ->
            foodsAdapter.updateData(foods)
        }

        binding.tbCart.setOnClickListener {
            isCartSelected = !isCartSelected
            updateToggleButtonState()
            Navigation.findNavController(it).navigate(R.id.cartFragment)
        }

        binding.tbHome.setOnClickListener {
            isHomeSelected = !isHomeSelected
            updateToggleButtonState()
            Navigation.findNavController(it).navigate(R.id.homepageFragment)
        }

        binding.ibMap.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.transitationMap)
        }

        return binding.root
    }
    private var isCartSelected = false
    private var isHomeSelected = false

    private fun updateToggleButtonState() {
        if (isCartSelected) {
            binding.tbCart.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbCart.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }

        if (isHomeSelected) {
            binding.tbHome.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbHome.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val temp: HomepageViewModel by viewModels()
        viewModel = temp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filteredFoods
        foodsAdapter = HomepageAdapter(requireContext(), mutableListOf(), viewModel)
        binding.rvHomeCardDesign.adapter = foodsAdapter

        searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("SearchView", "Query submitted: $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFoods(newText.orEmpty())
                Log.e("SearchView", "Query changed: $newText")
                return true
            }
        })
    }
}