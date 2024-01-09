package com.selin.fooddeliveryapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFoodToFavorite(): List<FavoriteFood>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFoodToFavorite(favoriteFood: FavoriteFood)

    @Delete
    suspend fun deleteFoodFromFavorite(favoriteFood: FavoriteFood)
}