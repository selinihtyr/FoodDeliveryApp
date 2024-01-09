package com.selin.fooddeliveryapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.databinding.ItemViewFavoriteCartBinding

class FavoriteAdapter(
    private var favoriteFoods: List<FavoriteFood>,
    private val foodCallbacks: FavoriteCallback
) : ListAdapter<FavoriteFood, FavoriteViewHolder>(FavoriteDiffUtil()) {

    interface FavoriteCallback {
        fun onClickDelete(favoriteFood: FavoriteFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemViewFavoriteCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding, foodCallbacks)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteFood = favoriteFoods[position]
        holder.bind(favoriteFood)
    }

    override fun getItemCount(): Int = favoriteFoods.size

    class FavoriteDiffUtil : DiffUtil.ItemCallback<FavoriteFood>() {
        override fun areItemsTheSame(oldItem: FavoriteFood, newItem: FavoriteFood): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteFood, newItem: FavoriteFood): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FavoriteFood>) {
        favoriteFoods = newData
        notifyDataSetChanged()
    }

}