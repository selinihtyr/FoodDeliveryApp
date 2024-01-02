package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.model.response.FoodCartListResponse
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse
import com.selin.fooddeliveryapp.data.remote.FoodApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepo(private val service: FoodApi) {
    suspend fun getAllFoods(): List<FoodListResponse> = withContext(Dispatchers.IO) {
        return@withContext service.getAllFoods().foods
    }

    suspend fun addFoodToCart(
        name: String,
        imageName: String,
        price: Int,
        orderQuantity: Int,
        username: String
    ) = withContext(Dispatchers.IO) {
        return@withContext service.addFoodToCart(name, imageName, price, orderQuantity, username)
    }

    suspend fun getCartFoods(username: String): List<FoodCartListResponse> =
        withContext(Dispatchers.IO) {
            try {
                val call = service.getCartFoods(username)
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
        service.deleteFoodFromCart(cartFoodId, username)
    }
}