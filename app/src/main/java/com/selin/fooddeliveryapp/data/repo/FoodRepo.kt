package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.dataSource.FoodDataSource
import com.selin.fooddeliveryapp.data.entity.Food
import com.selin.fooddeliveryapp.data.entity.FoodCart

class FoodRepo(private val foodDataSource: FoodDataSource) {
    suspend fun getAllFoods(): List<Food> = foodDataSource.getAllFoods()

    suspend fun addFoodToCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        username: String
    ) = foodDataSource.addFoodToCart(
        foodName,
        foodPrice,
        foodImageName,
        foodOrderQuantity,
        username
    )

    suspend fun getCartFoods(kullanici_adi: String): List<FoodCart> =
        foodDataSource.getCartFoods(kullanici_adi)

    suspend fun deleteFoodFromCart(sepet_yemek_id: Int, kullanici_adi: String) =
        foodDataSource.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)


}