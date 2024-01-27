package com.selin.fooddeliveryapp.data.source.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.selin.fooddeliveryapp.data.model.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getFavoritesDao(): FavoriteDao
}