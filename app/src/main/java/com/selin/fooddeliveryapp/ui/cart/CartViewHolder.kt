package com.selin.fooddeliveryapp.ui.cart

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.data.entity.FoodCart
import com.selin.fooddeliveryapp.databinding.CartDesignBinding

class CartViewHolder(
    val binding: CartDesignBinding,
    private val cartCallback: CartAdapter.CartCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cart: FoodCart) {
        val context = binding.root.context
        val card = binding
        card.tvFoodName.text = cart.foodName
        card.tvQuantity.text = cart.foodOrderQuantity.toString()
        val tbPrice = cart.foodPrice
        val totalPrice = cart.foodOrderQuantity * tbPrice
        card.tvPrice.text = "${totalPrice}₺"

        card.ibTrash.setOnClickListener {
            cartCallback.onClickDelete(cart)
        }

        loadImage(context = context, cart = cart)
    }

    private fun loadImage(context: Context, cart: FoodCart) {
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cart.foodImageName}"
        Glide.with(context)
            .load(url)
            .override(300, 300)
            .into(binding.ivFoodImage)
    }
}