package com.selin.fooddeliveryapp.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.selin.fooddeliveryapp.R
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.databinding.ItemViewHomeCardBinding
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import com.selin.fooddeliveryapp.utils.extension.Size
import com.selin.fooddeliveryapp.utils.extension.loadImage

class FoodViewHolder(
    val binding: ItemViewHomeCardBinding,
    private val foodCallbacks: FoodAdapter.FoodCallback
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(food: FoodResponse) {
        val context = binding.root.context
        binding.tvFoodName.text = food.name
        setupFoodInfo(context = context, foodPrice = food.price)
        setAddToCartListener(food)
        setDetailClickListener(food)
        loadImage(context = context, food = food)
    }

    private fun setupFoodInfo(context: Context, foodPrice: String) {
        val priceText = context.getString(R.string.price, foodPrice)
        binding.tvPrice.text = priceText
    }

    private fun setAddToCartListener(food: FoodResponse) {
        binding.ibAdd.setOnClickListener {
            foodCallbacks.onClickAddToCart(food)
        }
    }

    private fun setDetailClickListener(food: FoodResponse) {
        binding.ivFoodImage.setOnClickListener {
            foodCallbacks.onClickDetail(food)
        }
    }

    private fun loadImage(context: Context, food: FoodResponse) {
        val imageUrl = "${AppConstants.BASE_IMAGE_URL}${food.imageName}"
        binding.ivFoodImage.loadImage(imageUrl, size = Size(300, 300))
    }
}