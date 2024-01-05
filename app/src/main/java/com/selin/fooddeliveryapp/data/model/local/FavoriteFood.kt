package com.selin.fooddeliveryapp.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteFood(
    @PrimaryKey val id: Int,
    val foodName: String,
    val foodImage: String
)
