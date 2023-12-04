package com.selin.fooddeliveryapp.data.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodCart(
    @SerializedName("sepet_yemek_id") var cartFoodId: Int,
    @SerializedName("yemek_adi") var cartFoodName: String,
    @SerializedName("yemek_resim_adi") var cartImageName: String,
    @SerializedName("yemek_fiyat") var cartFoodPrice: Int,
    @SerializedName("yemek_siparis_adet") var cartFoodOrderQuantity: Int,
    @SerializedName("kullanici_adi") var username: String
): Parcelable {
}