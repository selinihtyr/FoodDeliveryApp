package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.data.remote.FoodApi
import com.selin.fooddeliveryapp.data.room.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepo(private val service: FoodApi, private val dao: FavoriteDao) {
    suspend fun getAllFoods(): List<FoodResponse> = withContext(Dispatchers.IO) {
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

    suspend fun getCartFoods(username: String): List<FoodCartResponse> =
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

    suspend fun getFoodToFavorite(): List<FavoriteFood> {
        return withContext(Dispatchers.IO) {
            dao.getFoodToFavorite()
        }
    }

    suspend fun saveFoodToFavorite(id: Int, foodName: String, foodImage: String) {
        val favoriteFood = FavoriteFood(id, foodName, foodImage)
        withContext(Dispatchers.IO) {
            dao.saveFoodToCart(favoriteFood)
        }
    }

    suspend fun deleteFoodFromFavorite(id: Int) = withContext(Dispatchers.IO) {
        val favoriteFood = FavoriteFood(id, "", "")
        withContext(Dispatchers.IO) {
            dao.deleteFoodFromFavorite(favoriteFood)
        }
    }
}