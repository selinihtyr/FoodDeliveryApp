package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.databinding.FragmentDetailBinding
import com.selin.fooddeliveryapp.ui.viewModel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initializeViews()
        setListeners()
        return binding.root
    }

    private fun initializeViews() {
        val foodName = arguments?.getString("yemek_adi")
        val foodPrice = arguments?.getString("yemek_fiyat")
        val foodImage = arguments?.getString("yemek_resim_adi")

        if (foodName != null && foodPrice != null && foodImage != null) {
            val foodPhoto = "http://kasimadalan.pe.hu/yemekler/resimler/$foodImage"
            loadImage(foodPhoto)
            displayFoodDetails(foodName, foodPrice)
        }
    }

    private fun setListeners() {
        binding.chipMinus.setOnClickListener { updateQuantity(-1) }
        binding.chipPlus.setOnClickListener { updateQuantity(1) }
        binding.chipAddCart.setOnClickListener { addToCart() }
        binding.tbCart3.setOnClickListener { navigateToCartFragment() }
        binding.tbHome3.setOnClickListener { navigateToHomeFragment() }
        binding.ivBack.setOnClickListener { navigateBack() }
    }

    private fun loadImage(imageUrl: String) {
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivFood)
    }

    private fun displayFoodDetails(name: String, price: String) {
        binding.tvFoodName.text = getString(R.string.food_name, name)
        binding.tvPrice.text = getString(R.string.price, price)
    }

    private fun updateQuantity(amount: Int) {
        val tvQuantity = binding.tvQuantity
        var quantity = tvQuantity.text.toString().toIntOrNull() ?: 0

        quantity += amount

        if (quantity in 1..9) {
            tvQuantity.text = quantity.toString()
        }
    }

    private fun addToCart() {
        val quantity = binding.tvQuantity.text.toString().toInt()
        val foodName = arguments?.getString("yemek_adi")
        val foodImage = arguments?.getString("yemek_resim_adi")
        val foodPrice = arguments?.getString("yemek_fiyat")?.toInt() ?: 0
        val username = Username.username

        viewModel.addFoodToCart(foodName!!, foodImage!!, foodPrice, quantity, username)

        Navigation.findNavController(binding.chipAddCart).navigate(R.id.detailFragment_to_cartFragment)
    }

    private fun navigateToCartFragment() {
        Navigation.findNavController(binding.chipAddCart).navigate(R.id.detailFragment_to_cartFragment)
    }

    private fun navigateToHomeFragment() {
        Navigation.findNavController(binding.tbHome3).navigate(R.id.homepageFragment)
    }

    private fun navigateBack() {
        view?.let { Navigation.findNavController(it).popBackStack() }
    }
}