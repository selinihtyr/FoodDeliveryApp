package com.selin.fooddeliveryapp.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.databinding.ItemViewFavoriteCartBinding
import com.selin.fooddeliveryapp.domain.FavoriteFood
import com.selin.fooddeliveryapp.utils.extension.Size
import com.selin.fooddeliveryapp.utils.extension.loadImage

class FavoriteViewHolder(
    val binding: ItemViewFavoriteCartBinding,
    private val foodCallbacks: FavoriteAdapter.FavoriteCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(favoriteFood: FavoriteFood) = with(binding) {
        binding.ivFoodImage.loadImage(
            imageUrl = favoriteFood.imageUrl,
            size = Size(300, 300)
        )
        tvFoodName.text = favoriteFood.name
        ibDontLike.setOnClickListener {
            foodCallbacks.onClickDelete(favoriteFood)
        }
    }
}