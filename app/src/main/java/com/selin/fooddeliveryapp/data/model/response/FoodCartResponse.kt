package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName

data class FoodCartResponse(
    @SerializedName("sepet_yemekler")
    val foods: List<FoodCartListResponse>,
    @SerializedName("success")
    val success: Int
)