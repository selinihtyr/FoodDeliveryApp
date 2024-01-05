package com.selin.fooddeliveryapp.data.room

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.selin.fooddeliveryapp.data.model.local.FavoriteFood

@Database(entities = [FavoriteFood::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun getFavoritesDao() : FavoriteDao
}