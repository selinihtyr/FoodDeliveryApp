package com.selin.fooddeliveryapp.data.dataSource

import com.selin.fooddeliveryapp.data.model.remote.FoodResponse
import com.selin.fooddeliveryapp.data.model.remote.FoodCartResponse
import com.selin.fooddeliveryapp.data.remote.FoodApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDataSource(private val api: FoodApi) {
    suspend fun getAllFoods(): List<FoodResponse> = withContext(Dispatchers.IO) {
        return@withContext api.getAllFoods().foods
    }

    suspend fun addFoodToCart(
        name: String,
        price: Int,
        imageName: String,
        orderQuantity: Int,
        username: String
    ) {
        this.api.addFoodToCart(
            name,
            imageName,
            price,
            orderQuantity,
            username
        )
    }

    suspend fun getCartFoods(username: String): List<FoodCartResponse> = withContext(Dispatchers.IO) {
        try {
            val call = api.getCartFoods(username)
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                return@withContext response.body()!!.foods
            } else {
                return@withContext emptyList()
            }
        } catch (e: Exception) {
            return@withContext emptyList()
        }
    }

    suspend fun deleteFoodFromCart(cartFoodId: Int, username: String) {
        api.deleteFoodFromCart(cartFoodId, username)
    }
}