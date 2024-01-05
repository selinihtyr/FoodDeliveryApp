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
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.databinding.FragmentDetailBinding
import com.selin.fooddeliveryapp.utils.loadImage
import com.selin.fooddeliveryapp.utils.toSafeInt
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var food: FoodResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initViews()
        observe()
    }

    private fun initVariables() {
        food = requireArguments().getParcelable(KEY_FOOD)!!
    }

    private fun initViews() = with(binding) {
        displayFoodDetails(food)
        tbCartDetail.setOnClickListener { findNavController().navigate(R.id.detailFragment_to_cartFragment) }
        tbHomeDetail.setOnClickListener { findNavController().navigate(R.id.homepageFragment) }
        ivBack.setOnClickListener { findNavController().popBackStack() }
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
            val quantityText = viewModel.quantityText.value ?: 1
            val totalPrice = food.price.toSafeInt() * newQuantity * quantityText
            tvPrice.text = getString(R.string.price, totalPrice.toString())
        }
    }

    private fun displayFoodDetails(food: FoodResponse) = with(binding) {
        val photo = "http://kasimadalan.pe.hu/yemekler/resimler/${food.imageName}"
        binding.ivFoodImage.loadImage(imageUrl = photo)
        tvFoodName.text = food.name
        val quantityText = viewModel.quantityText.value ?: 1
        val totalPrice = food.price.toSafeInt() * viewModel.quantity.value!! * quantityText
        tvPrice.text = getString(R.string.price, totalPrice.toString())
    }

    private fun addToCart() {
        val quantity = viewModel.quantity.value ?: 1
        viewModel.addToCart(
            food = food,
            orderQuantity = quantity,
            username = username
        )
        findNavController().navigate(R.id.detailFragment_to_cartFragment)
    }

    companion object {
        const val KEY_FOOD = "key_food"
    }

}