package com.selin.fooddeliveryapp.data.source.locale

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.selin.fooddeliveryapp.data.model.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFoodToFavorite(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFoodToFavorite(favoriteFood: FavoriteEntity)

    @Delete
    suspend fun deleteFoodFromFavorite(favoriteFood: FavoriteEntity)
}