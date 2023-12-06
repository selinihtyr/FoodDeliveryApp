package com.selin.fooddeliveryapp.data.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodResponse(
    @SerializedName("yemek_id") var id: Int,
    @SerializedName("yemek_adi") var name: String,
    @SerializedName("yemek_resim_adi") var imageName: String?,
    @SerializedName("yemek_fiyat") var price: String
) : Parcelable