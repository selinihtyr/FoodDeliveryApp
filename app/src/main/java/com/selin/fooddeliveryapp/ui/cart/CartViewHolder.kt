package com.selin.fooddeliveryapp.ui.cart

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.data.entity.FoodCart
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

class CartViewHolder(
    val binding: ItemViewCartCardBinding,
    private val cartCallback: CartAdapter.CartCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cart: FoodCart) {
        val context = binding.root.context
        val card = binding
        card.tvFoodName.text = cart.cartFoodName
        card.tvQuantity.text = cart.cartFoodOrderQuantity.toString()
        val tbPrice = cart.cartFoodPrice
        val totalPrice = cart.cartFoodOrderQuantity * tbPrice
        card.tvPrice.text = "${totalPrice}₺"

        card.ibTrash.setOnClickListener {
            cartCallback.onClickDelete(cart)
        }

        loadImage(context = context, cart = cart)
    }

    private fun loadImage(context: Context, cart: FoodCart) {
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cart.cartImageName}"
        Glide.with(context)
            .load(url)
            .override(300, 300)
            .into(binding.ivFoodImage)
    }
}