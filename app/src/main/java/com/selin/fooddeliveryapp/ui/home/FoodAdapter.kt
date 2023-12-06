package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.model.remote.FoodResponse
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodAdapter(
    private var foods: List<FoodResponse>,
    private val foodCallbacks: FoodCallback
) : RecyclerView.Adapter<FoodViewHolder>() {

    interface FoodCallback {
        fun onClickFavoriteButton(food: FoodResponse)
        fun onClickDetail(food: FoodResponse)
        fun onClickAddToCart(food: FoodResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemViewHomeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
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