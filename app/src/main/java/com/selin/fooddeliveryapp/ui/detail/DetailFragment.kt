package com.selin.fooddeliveryapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.local.Credentials
import com.selin.fooddeliveryapp.databinding.FragmentDetailBinding
import com.selin.fooddeliveryapp.ui.cart.CartViewModel
import com.selin.fooddeliveryapp.utils.loadImage
import com.selin.fooddeliveryapp.utils.toSafeInt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding){
        val name = arguments?.getString("yemek_adi")
        val price = arguments?.getString("yemek_fiyat")
        val image = arguments?.getString("yemek_resim_adi")

        displayFoodDetails(name = name, price = price, image = image)
        chipMinus.setOnClickListener { updateQuantity(-1) }
        chipPlus.setOnClickListener { updateQuantity(1) }
        chipAddCart.setOnClickListener { addToCart() }
        tbCartDetail.setOnClickListener { findNavController().navigate(R.id.detailFragment_to_cartFragment) }
        tbHomeDetail.setOnClickListener { findNavController().navigate(R.id.homepageFragment) }
        ivBack.setOnClickListener { view?.let { findNavController().popBackStack() } }
    }

    private fun displayFoodDetails(name: String?, price: String?, image: String?) = with(binding) {
        if(image != null){
            val photo = "http://kasimadalan.pe.hu/yemekler/resimler/$image"
            binding.ivFoodImage.loadImage(imageUrl = photo)
        }
        tvFoodName.text = name
        if(price != null){
            tvPrice.text = getString(R.string.price, price)
        }
    }

    private fun updateQuantity(amount: Int) {
        val tvQuantity = binding.tvQuantity
        var quantity = tvQuantity.text.toString().toSafeInt()

        quantity += amount

        if (quantity in 1..9) {
            tvQuantity.text = quantity.toString()
        }
    }

    private fun addToCart() {
        val quantity = binding.tvQuantity.text.toString().toSafeInt()
        val name = arguments?.getString("yemek_adi")
        val image = arguments?.getString("yemek_resim_adi")
        val price = arguments?.getString("yemek_fiyat").toSafeInt()
        val username = Credentials.username

        viewModel.addFoodToCart(name!!, image!!, price, quantity, username)

        findNavController().navigate(R.id.detailFragment_to_cartFragment)
    }
}