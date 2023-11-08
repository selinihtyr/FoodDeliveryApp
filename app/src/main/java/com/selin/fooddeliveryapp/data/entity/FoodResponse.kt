package com.selin.fooddeliveryapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("yemekler")
    var foods: List<Food>,
    var success: Int
) {
}