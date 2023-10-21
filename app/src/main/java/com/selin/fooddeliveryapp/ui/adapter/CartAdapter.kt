package com.selin.fooddeliveryapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.selin.fooddeliveryapp.data.entity.FoodsCart
import com.selin.fooddeliveryapp.data.entity.Username
import com.selin.fooddeliveryapp.databinding.CartDesignBinding
import com.selin.fooddeliveryapp.ui.viewModel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(var mContext: Context, var cartList: List<FoodsCart>, var viewModel: CartViewModel)
    : RecyclerView.Adapter<CartAdapter.CartDesignHolder>() {
    inner class CartDesignHolder(var design: CartDesignBinding): RecyclerView.ViewHolder(design.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartDesignHolder {
        val binding = CartDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CartDesignHolder(binding)
    }

    fun updateData(newData: List<FoodsCart>) {
        cartList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartDesignHolder, position: Int) {
        if (position < cartList.size) {
            val cart = cartList[position]
            val c = holder.design
            c.tvFoodName.text = cart.yemek_adi
            c.tvQuantity.text = cart.yemek_siparis_adet.toString()
            val tbPrice = cart.yemek_fiyat
            val totalPrice = cart.yemek_siparis_adet * tbPrice
            c.tvPrice.text = "${totalPrice}₺"


        c.ibTrash.setOnClickListener {
            Snackbar.make(it, "Are you sure you want to delete from the cart?", Snackbar.LENGTH_LONG)
                .setAction("Yes") {
            deleteFromCart(cart, position)
                }.show()
            }
        }

        val food = cartList[position]
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext)
            .load(url)
            .override(300, 300)
            .into(holder.design.ivFood)
    }

    fun deleteFromCart(cart: FoodsCart, position: Int) {
        val itemId = cart.sepet_yemek_id
        val username = Username.username

        viewModel.delete(itemId, username) {
            CoroutineScope(Dispatchers.Main).launch {
                val updatedCartList = viewModel.foodsCartList.value?.toMutableList()
                updatedCartList?.removeAt(position)
                viewModel.foodsCartList.value = updatedCartList
                notifyItemRemoved(position)
                viewModel.updateCart()
            }
        }
    }
}