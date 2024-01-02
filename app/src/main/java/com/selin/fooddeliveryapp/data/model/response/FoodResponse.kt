package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    @SerializedName("yemekler")
    val foods: List<FoodListResponse>,
    @SerializedName("success")
    val success: Int
)