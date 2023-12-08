package com.selin.fooddeliveryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse
import com.selin.fooddeliveryapp.databinding.FragmentHomepageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var adapter: FoodAdapter
    private var isCartSelected = false
    private var isHomeSelected = false
    private val viewModel: HomePageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initViews()
        observe()
    }

    private fun initVariables() {
        adapter = FoodAdapter(
            foods = mutableListOf(),
            foodCallbacks = object : FoodAdapter.FoodCallback {
                override fun onClickFavoriteButton(food: FoodListResponse) {

                }

                override fun onClickDetail(food: FoodListResponse) {
                    val bundle = Bundle().apply {
                        putString("yemek_adi", food.name)
                        putString("yemek_fiyat", food.price)
                        putString("yemek_resim_adi", food.imageName)
                    }
                    findNavController().navigate(R.id.transitationDetail, bundle)
                }

                override fun onClickAddToCart(food: FoodListResponse) {
                    viewModel.addToCart(food)
                }
            })
    }

    private fun initViews() = with(binding) {
        tbCartHome.setOnClickListener { toggleCartSelection() }
        tbHomeHome.setOnClickListener { toggleHomeSelection() }
        ibMap.setOnClickListener { navigateToMapFragment() }
        ibLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.homepageToSignIn)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFoods(newText.orEmpty())
                return true
            }
        })

        rvHome.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rvHome.adapter = adapter
    }

    private fun observe() {
        viewModel.filteredFoods.observe(viewLifecycleOwner) { foods ->
            adapter.updateData(foods)
        }

        lifecycleScope.launch {
            viewModel.showMessage.collectLatest { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleCartSelection() {
        isCartSelected = !isCartSelected
        updateToggleButtonState(binding.tbCartHome, isCartSelected)
        findNavController().navigate(R.id.cartFragment)
    }

    private fun toggleHomeSelection() {
        isHomeSelected = !isHomeSelected
        updateToggleButtonState(binding.tbHomeHome, isHomeSelected)
        findNavController().navigate(R.id.homepageFragment)
    }

    private fun navigateToMapFragment() {
        findNavController().navigate(R.id.mapFragment)
    }

    private fun updateToggleButtonState(button: Button, isSelected: Boolean) {
        button.setBackgroundResource(if (isSelected) R.drawable.tbutton_background else R.drawable.tbutton_background)
    }
}
