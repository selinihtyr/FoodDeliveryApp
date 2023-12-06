package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.dataSource.FoodDataSource
import com.selin.fooddeliveryapp.data.model.remote.FoodResponse
import com.selin.fooddeliveryapp.data.model.remote.FoodCartResponse

class FoodRepo(private val dataSource: FoodDataSource) {
    suspend fun getAllFoods(): List<FoodResponse> = dataSource.getAllFoods()

    suspend fun addFoodToCart(
        name: String,
        imageName: String,
        price: Int,
        orderQuantity: Int,
        username: String
    ) = dataSource.addFoodToCart(
        name,
        price,
        imageName,
        orderQuantity,
        username
    )

    suspend fun getCartFoods(username: String): List<FoodCartResponse> =
        dataSource.getCartFoods(username)

    suspend fun deleteFoodFromCart(cartFoodId: Int, username: String) =
        dataSource.deleteFoodFromCart(cartFoodId, username)
}