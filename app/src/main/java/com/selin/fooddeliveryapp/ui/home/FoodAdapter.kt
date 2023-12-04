package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.remote.Food
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodAdapter(
    private var foods: List<Food>,
    private val foodCallbacks: FoodCallback
) : RecyclerView.Adapter<FoodViewHolder>() {

    interface FoodCallback {
        fun onClickFavoriteButton(food: Food)
        fun onClickDetail(food: Food)
        fun onClickAddToCart(food: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemViewHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
    }

    fun updateData(newData: List<Food>) {
        foods = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foods.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }
}