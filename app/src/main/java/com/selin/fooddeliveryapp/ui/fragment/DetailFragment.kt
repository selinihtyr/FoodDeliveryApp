package com.selin.fooddeliveryapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
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
        val ratingBar = view?.findViewById<RatingBar>(R.id.ratingBar)
        val foodName = arguments?.getString("yemek_adi")
        val foodPrice = arguments?.getString("yemek_fiyat")
        val foodPhoto = "http://kasimadalan.pe.hu/yemekler/resimler/${arguments?.getString("yemek_resim_adi")}"

        Glide.with(this)
            .load(foodPhoto)
            .into(binding.ivFood)

        binding.tvFoodName.text = foodName
        binding.tvPrice.text = "$foodPrice₺"

        binding.tbCart3.setOnClickListener {
            isCartSelected = !isCartSelected
            updateToggleButtonState()
            Navigation.findNavController(it).navigate(R.id.detailFragment_to_cartFragment)
        }
        binding.tbHome3.setOnClickListener {
            isHomeSelected = !isHomeSelected
            updateToggleButtonState()
            Navigation.findNavController(it).navigate(R.id.homepageFragment)
        }

        binding.ivBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        if (ratingBar != null) {
            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                Toast.makeText(requireContext(), "Rating: $rating", Toast.LENGTH_SHORT).show()
            }
        }

        val tvQuantity = binding.tvQuantity
        var quantity = tvQuantity.text.toString().toInt()

        binding.chipMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                tvQuantity.text = quantity.toString()
            }
        }

        binding.chipPlus.setOnClickListener {
            if(quantity < 9)
                quantity++
            tvQuantity.text = quantity.toString()
        }

        binding.chipAddCart.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.detailFragment_to_cartFragment)
            val quantity = tvQuantity.text.toString().toInt()
            val foodName = arguments?.getString("yemek_adi")
            val foodImage = arguments?.getString("yemek_resim_adi")
            val foodPrice = foodPrice!!.toInt()
            val username = Username.username

            viewModel.addFoodToCart(foodName!!, arguments?.getString("yemek_resim_adi")!!, foodPrice!!.toInt(), quantity, Username.username)
    }
        return binding.root
    }

    private var isCartSelected = false
    private var isHomeSelected = false

    private fun updateToggleButtonState() {
        if (isCartSelected) {
            binding.tbCart3.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbCart3.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }

        if (isHomeSelected) {
            binding.tbHome3.setBackgroundResource(R.drawable.tbutton_background) // Seçilen arka plan rengi
        } else {
            binding.tbHome3.setBackgroundResource(R.drawable.tbutton_background) // Seçilmemiş arka plan rengi
        }
    }
}