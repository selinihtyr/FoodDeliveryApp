package com.selin.fooddeliveryapp.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.databinding.ItemViewFavoriteCartBinding

class FavoriteViewHolder(
    val binding: ItemViewFavoriteCartBinding,
    private val foodCallbacks: FavoriteAdapter.FavoriteCallback,
    private var isFavorite: Boolean = false
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(favoriteFood: FavoriteFood) = with(binding){
        val context = binding.root.context
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${favoriteFood.foodImage}"
        Glide.with(context).load(imageUrl).override(300, 300).into(ivFoodImage)
        tvFoodName.text = favoriteFood.foodName
        ibLike.setOnClickListener {
            foodCallbacks.onClickDelete(favoriteFood)
        }
    }
}