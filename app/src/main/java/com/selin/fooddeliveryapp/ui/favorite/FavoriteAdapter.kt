package com.selin.fooddeliveryapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.selin.fooddeliveryapp.data.model.entity.FavoriteEntity
import com.selin.fooddeliveryapp.databinding.ItemViewFavoriteCartBinding

class FavoriteAdapter(
    private var favoriteFoods: List<FavoriteEntity>,
    private val foodCallbacks: FavoriteCallback
) : ListAdapter<FavoriteEntity, FavoriteViewHolder>(FavoriteDiffUtil()) {

    interface FavoriteCallback {
        fun onClickDelete(favoriteFood: FavoriteEntity)
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

    class FavoriteDiffUtil : DiffUtil.ItemCallback<FavoriteEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem == newItem
        }
    }

    fun updateData(newData: List<FavoriteEntity>) {
        favoriteFoods = newData
        notifyDataSetChanged()
    }

}