package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName
import com.selin.fooddeliveryapp.data.model.remote.Food

data class FoodResponse(
    @SerializedName("yemekler")
    var foods: List<Food>,
    var success: Int
) {
}