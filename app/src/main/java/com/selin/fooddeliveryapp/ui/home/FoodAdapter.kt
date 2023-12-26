package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodAdapter(
    private var foods: List<FoodListResponse>,
    private val foodCallbacks: FoodCallback
) : ListAdapter<FoodListResponse, FoodViewHolder>(ModelDiffCallback()) {

    interface FoodCallback {
        fun onClickFavoriteButton(food: FoodListResponse)
        fun onClickDetail(food: FoodListResponse)
        fun onClickAddToCart(food: FoodListResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding =
            ItemViewHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
    }

    class ModelDiffCallback : DiffUtil.ItemCallback<FoodListResponse>() {
        override fun areItemsTheSame(
            oldItem: FoodListResponse,
            newItem: FoodListResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FoodListResponse,
            newItem: FoodListResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FoodListResponse>) {
        foods = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }
}