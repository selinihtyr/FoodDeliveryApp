package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodAdapter(
    private var foods: List<FoodResponse>,
    private val foodCallbacks: FoodCallback
) : ListAdapter<FoodResponse,FoodViewHolder>(FoodDiffUtil()) {

    interface FoodCallback {
        fun onClickFavoriteButton(food: FoodResponse, isFavorite: Boolean)
        fun onClickDetail(food: FoodResponse)
        fun onClickAddToCart(food: FoodResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemViewHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
    }

    class FoodDiffUtil : DiffUtil.ItemCallback<FoodResponse>() {
        override fun areItemsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodResponse, newItem: FoodResponse): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FoodResponse>) {
        foods = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }
}