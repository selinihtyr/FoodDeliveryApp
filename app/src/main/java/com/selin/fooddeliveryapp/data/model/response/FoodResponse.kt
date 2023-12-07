package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse

data class FoodResponse(
    @SerializedName("yemekler")
    val foods: List<FoodListResponse>,
    @SerializedName("success")
    val success: Int
)