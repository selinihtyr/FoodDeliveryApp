package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import com.selin.fooddeliveryapp.data.room.FavoriteDao
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import com.selin.fooddeliveryapp.data.remote.FoodApi
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

    suspend fun getFoodToFavorite(food: FavoriteFood) {
        withContext(Dispatchers.IO) {
            dao.getFoodToFavorite().forEach {
                if (it.foodName == food.foodName) {
                    return@withContext
                }
            }
        }
    }

    suspend fun saveFoodToFavorite(favoriteFood: FavoriteFood) {
        withContext(Dispatchers.IO) {
            dao.saveFoodToCart(favoriteFood)
        }
    }

    suspend fun deleteFoodToFavorite(favoriteFood: FavoriteFood) {
        withContext(Dispatchers.IO) {
            dao.deleteFoodToFavorite(favoriteFood)
        }
    }
}