package com.selin.fooddeliveryapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.selin.fooddeliveryapp.domain.FavoriteFood
import com.selin.fooddeliveryapp.utils.constans.AppConstants

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val foodName: String,
    val foodImage: String
)

fun FavoriteEntity.toFood(): FavoriteFood {
    return FavoriteFood(
        id = id,
        name = foodName,
        imageUrl = "${AppConstants.BASE_IMAGE_URL}${foodImage}"
    )
}
