package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName
import com.selin.fooddeliveryapp.data.model.remote.FoodCartResponse

data class FoodCartResponse(
    @SerializedName("sepet_yemekler")
    val foods: List<FoodCartResponse>,
    @SerializedName("success")
    val success: Int
)