package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.dataSource.FoodDataSource
import com.selin.fooddeliveryapp.data.model.remote.Food
import com.selin.fooddeliveryapp.data.model.remote.FoodCart

class FoodRepo(private val dataSource: FoodDataSource) {
    suspend fun getAllFoods(): List<Food> = dataSource.getAllFoods()

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

    suspend fun getCartFoods(username: String): List<FoodCart> =
        dataSource.getCartFoods(username)

    suspend fun deleteFoodFromCart(cartFoodId: Int, username: String) =
        dataSource.deleteFoodFromCart(cartFoodId, username)


}