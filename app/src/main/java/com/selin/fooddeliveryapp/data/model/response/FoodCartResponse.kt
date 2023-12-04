package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName
import com.selin.fooddeliveryapp.data.model.remote.FoodCart

data class FoodCartResponse(
    @SerializedName("sepet_yemekler")
    var foods: List<FoodCart>,
    var success: Int
) {
}