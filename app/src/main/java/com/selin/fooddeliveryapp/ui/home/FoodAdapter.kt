package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodAdapter(
    private var foods: List<FoodListResponse>,
    private val foodCallbacks: FoodCallback
) : RecyclerView.Adapter<FoodViewHolder>() {

    interface FoodCallback {
        fun onClickFavoriteButton(food: FoodListResponse)
        fun onClickDetail(food: FoodListResponse)
        fun onClickAddToCart(food: FoodListResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemViewHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
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