package com.selin.fooddeliveryapp.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.selin.fooddeliveryapp.domain.FavoriteFood
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import java.io.Serializable

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodName: String,
    val foodImage: String
) : Serializable

fun FavoriteEntity.toFood(): FavoriteFood {
    return FavoriteFood(
        id = id,
        name = foodName,
        imageUrl = "${AppConstants.BASE_IMAGE_URL}${foodImage}"
    )
}
