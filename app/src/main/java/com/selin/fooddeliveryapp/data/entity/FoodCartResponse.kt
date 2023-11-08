package com.selin.fooddeliveryapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodCartResponse(
    @SerializedName("sepet_yemekler")
    var foods: List<FoodCart>,
    var success: Int
) {
}