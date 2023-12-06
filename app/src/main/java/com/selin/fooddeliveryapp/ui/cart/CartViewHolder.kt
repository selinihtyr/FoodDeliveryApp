package com.selin.fooddeliveryapp.ui.cart

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.data.model.remote.FoodCartResponse
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

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

        //loadImage(cart = cart)
    }

    private fun loadImage(context: Context, cart: FoodCartResponse) {
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cart.imageName}"
        Glide.with(context)
            .load(url)
            .override(300, 300)
            .into(binding.ivFoodImage)
    }
}