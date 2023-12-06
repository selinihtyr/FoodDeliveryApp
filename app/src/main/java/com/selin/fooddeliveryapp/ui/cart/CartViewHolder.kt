package com.selin.fooddeliveryapp.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.remote.FoodCartResponse
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding
import com.selin.fooddeliveryapp.utils.Size
import com.selin.fooddeliveryapp.utils.loadImage

class CartViewHolder(
    val binding: ItemViewCartCardBinding,
    private val cartCallback: CartAdapter.CartCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cart: FoodCartResponse) = with(binding) {
        tvFoodName.text = cart.name
        tvQuantity.text = cart.orderQuantity.toString()
        val tbPrice = cart.price
        val totalPrice = cart.orderQuantity * tbPrice
        tvPrice.text = "${totalPrice}₺"
        ibTrash.setOnClickListener {
            cartCallback.onClickDelete(cart)
        }

        loadImage(cart = cart)
    }

    private fun loadImage(cart: FoodCartResponse) {
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${cart.imageName}"
        binding.ivFoodImage.loadImage(imageUrl = imageUrl, size = Size(100, 100))
    }
}