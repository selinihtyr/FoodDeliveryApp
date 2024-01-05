package com.selin.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

class CartAdapter(
    private var list: List<FoodCartResponse>,
    private val cartCallback: CartCallback
) : ListAdapter<FoodCartResponse,CartViewHolder>(CartDiffUtil()) {

    interface CartCallback {
        fun onClickDelete(cart: FoodCartResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemViewCartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, cartCallback)
    }

    class CartDiffUtil : DiffUtil.ItemCallback<FoodCartResponse>() {
        override fun areItemsTheSame(oldItem: FoodCartResponse, newItem: FoodCartResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodCartResponse, newItem: FoodCartResponse): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FoodCartResponse>) {
        list = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(list[position])
    }
}

