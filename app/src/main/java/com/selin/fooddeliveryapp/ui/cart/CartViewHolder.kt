package com.selin.fooddeliveryapp.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import com.selin.fooddeliveryapp.utils.extension.Size
import com.selin.fooddeliveryapp.utils.extension.loadImage

class CartViewHolder(
    val binding: ItemViewCartCardBinding,
    private val cartCallback: CartAdapter.CartCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cart: FoodCartResponse) = with(binding) {
        val context = binding.root.context
        tvFoodName.text = cart.name
        tvQuantity.text = cart.orderQuantity.toString()
        val tbPrice = cart.price
        val totalPrice = cart.orderQuantity * tbPrice
        tvPrice.text = context.getString(R.string.price, totalPrice.toString())
        ibTrash.setOnClickListener {
            cartCallback.onClickDelete(cart)
        }
        loadImage(cart = cart)
    }

    private fun loadImage(cart: FoodCartResponse) {
        val imageUrl = "${AppConstants.BASE_IMAGE_URL}${cart.imageName}"
        binding.ivFoodImage.loadImage(imageUrl = imageUrl, size = Size(100, 100))
    }
}