package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.dataSource.FoodDataSource
import com.selin.fooddeliveryapp.data.model.remote.Food
import com.selin.fooddeliveryapp.data.model.remote.FoodCart

class FoodRepo(private val dataSource: FoodDataSource) {
    suspend fun getAllFoods(): List<Food> = dataSource.getAllFoods()

    suspend fun addFoodToCart(
        foodName: String,
        foodImageName: String,
        foodPrice: Int,
        foodOrderQuantity: Int,
        username: String
    ) = dataSource.addFoodToCart(
        foodName,
        foodPrice,
        foodImageName,
        foodOrderQuantity,
        username
    )

    suspend fun getCartFoods(username: String): List<FoodCart> =
        dataSource.getCartFoods(username)

    suspend fun deleteFoodFromCart(cartFoodId: Int, username: String) =
        dataSource.deleteFoodFromCart(cartFoodId, username)


}