package com.selin.fooddeliveryapp.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.entity.Food
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding

class FoodViewHolder(
    val binding: ItemViewHomeCardBinding,
    private val foodCallbacks: FoodAdapter.FoodCallback,
    private var isFavorite: Boolean = false
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(food: Food) {
        val context = binding.root.context
        binding.tvFoodName.text = food.name
        setupFoodInfo(context = context, foodPrice = food.price)
        setAddToCartListener(food)
        setDetailClickListener(food)
        setLikeClickListener(food)
        loadImage(context = context, food = food)
    }

    private fun setupFoodInfo(context: Context, foodPrice: String) {
        val priceText = context.getString(R.string.price, foodPrice)
        binding.tvPrice.text = priceText
    }

    private fun setAddToCartListener(food: Food) {
        binding.ibAdd.setOnClickListener {
            foodCallbacks.onClickAddToCart(food)
        }
    }

    private fun setDetailClickListener(food: Food) {
        binding.ivFoodImage.setOnClickListener {
            foodCallbacks.onClickDetail(food)
        }
    }

    private fun setLikeClickListener(food: Food) {
        binding.ibLike.setOnClickListener {
            foodCallbacks.onClickFavoriteButton(food)
            isFavorite = !isFavorite
            if (isFavorite) {
                binding.ibLike.setImageResource(R.drawable.red_heart)
            } else {
                binding.ibLike.setImageResource(R.drawable.like)
            }
        }
    }

    private fun loadImage(context: Context, food: Food) {
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${food.imageName}"
        Glide.with(context)
            .load(imageUrl)
            .override(300, 300)
            .into(binding.ivFoodImage)
    }
}