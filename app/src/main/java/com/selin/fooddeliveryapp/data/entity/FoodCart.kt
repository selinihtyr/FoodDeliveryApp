package com.selin.fooddeliveryapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodCart(
    @SerializedName("sepet_yemek_id") var cartFoodId: Int,
    @SerializedName("yemek_adi") var foodName: String,
    @SerializedName("yemek_resim_adi") var foodImageName: String,
    @SerializedName("yemek_fiyat") var foodPrice: Int,
    @SerializedName("yemek_siparis_adet") var foodOrderQuantity: Int,
    @SerializedName("kullanici_adi") var username: String
) {
}