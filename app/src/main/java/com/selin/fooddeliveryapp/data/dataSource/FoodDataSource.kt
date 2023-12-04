package com.selin.fooddeliveryapp.data.dataSource

import com.selin.fooddeliveryapp.data.model.remote.Food
import com.selin.fooddeliveryapp.data.model.remote.FoodCart
import com.selin.fooddeliveryapp.data.remote.FoodApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDataSource(private val api: FoodApi) {
    suspend fun getAllFoods(): List<Food> = withContext(Dispatchers.IO) {
        return@withContext api.getAllFoods().foods
    }

    suspend fun addFoodToCart(
        name: String,
        price: Int,
        imageName: String,
        orderQuantity: Int,
        username: String
    ) {
        val answer = this.api.addFoodToCart(
            name,
            imageName,
            price,
            orderQuantity,
            username
        )
    }

    suspend fun getCartFoods(username: String): List<FoodCart> = withContext(Dispatchers.IO) {
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
        val answer = api.deleteFoodFromCart(cartFoodId, username)
    }
}