package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName

data class FoodListResponse(
    @SerializedName("yemekler")
    val foods: List<FoodResponse>,
    @SerializedName("success")
    val success: Int
)