package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName

data class FoodCartListResponse(
    @SerializedName("sepet_yemekler")
    val foods: List<FoodCartResponse>,
    @SerializedName("success")
    val success: Int
)