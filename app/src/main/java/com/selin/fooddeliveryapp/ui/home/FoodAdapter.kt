package com.selin.fooddeliveryapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.data.entity.Food
import com.selin.fooddeliveryapp.databinding.CardDesignBinding

class FoodAdapter(
    private var foodsList: List<Food>,
    private val foodCallbacks: FoodCallback
) : RecyclerView.Adapter<FoodViewHolder>() {

    interface FoodCallback {
        fun onClickFavoriteButton(food: Food)
        fun onClickDetail(food: Food)
        fun onClickAddToCart(food: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, foodCallbacks)
    }

    fun updateData(newData: List<Food>) {
        foodsList = newData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = foodsList.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val food = foodsList[position]
        holder.bind(food)
    }
}