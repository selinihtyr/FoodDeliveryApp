package com.selin.fooddeliveryapp.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.response.FoodCartListResponse
import com.selin.fooddeliveryapp.databinding.ItemViewCartCardBinding

class CartAdapter(
    private var list: List<FoodCartListResponse>,
    private val cartCallback: CartCallback
) : ListAdapter<FoodCartListResponse,CartViewHolder>(CartDiffUtil()) {

    interface CartCallback {
        fun onClickDelete(cart: FoodCartListResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemViewCartCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, cartCallback)
    }

    class CartDiffUtil : DiffUtil.ItemCallback<FoodCartListResponse>() {
        override fun areItemsTheSame(oldItem: FoodCartListResponse, newItem: FoodCartListResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodCartListResponse, newItem: FoodCartListResponse): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FoodCartListResponse>) {
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

