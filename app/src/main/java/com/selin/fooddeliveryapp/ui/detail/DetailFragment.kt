package com.selin.fooddeliveryapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.local.Credentials.Companion.username
import com.selin.fooddeliveryapp.databinding.FragmentDetailBinding
import com.selin.fooddeliveryapp.utils.loadImage
import com.selin.fooddeliveryapp.utils.toSafeInt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

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
        observe()
    }

    private fun initViews() = with(binding) {
        val name = arguments?.getString("yemek_adi")
        val price = arguments?.getString("yemek_fiyat")
        val image = arguments?.getString("yemek_resim_adi")
        displayFoodDetails(name = name, price = price, image = image)
        tbCartDetail.setOnClickListener { findNavController().navigate(R.id.detailFragment_to_cartFragment) }
        tbHomeDetail.setOnClickListener { findNavController().navigate(R.id.homepageFragment) }
        ivBack.setOnClickListener { view?.let { findNavController().popBackStack() } }
    }

    private fun observe() = with(binding) {
        chipAddCart.setOnClickListener { addToCart() }
        chipMinus.setOnClickListener { viewModel.decreaseOrderQuantity() }
        chipPlus.setOnClickListener { viewModel.increaseOrderQuantity() }
        tvPrice.text = getString(R.string.price, tvPrice.text.toString())
        tvQuantity.text = "${viewModel.quantity.value}"

        lifecycleScope.launchWhenStarted {
            viewModel.message.collect {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
            }
        }
        viewModel.quantity.observe(viewLifecycleOwner) { newQuantity ->
            tvQuantity.text = "$newQuantity"

            val price = arguments?.getString("yemek_fiyat").toSafeInt() ?: 0
            val quantityText = viewModel.quantityText.value ?: 1
            val totalPrice = price * newQuantity * quantityText

            tvPrice.text = getString(R.string.price, totalPrice.toString())
        }
    }

    private fun displayFoodDetails(name: String?, price: String?, image: String?) = with(binding) {
        if (image != null) {
            val photo = "http://kasimadalan.pe.hu/yemekler/resimler/$image"
            binding.ivFoodImage.loadImage(imageUrl = photo)
        }
        tvFoodName.text = name

        if (price != null) {
            val quantityText = viewModel.quantityText.value ?: 1
            val totalPrice = price.toSafeInt() * viewModel.quantity.value!! * quantityText
            tvPrice.text = getString(R.string.price, totalPrice.toString())
        }
    }

    private fun addToCart() {
        val quantity = viewModel.quantity.value ?: 1
        val name = arguments?.getString("yemek_adi")
        val image = arguments?.getString("yemek_resim_adi")
        val price = arguments?.getString("yemek_fiyat").toSafeInt()
        viewModel.addToCart(name!!, image!!, price, quantity, username)
        findNavController().navigate(R.id.detailFragment_to_cartFragment)
    }
}