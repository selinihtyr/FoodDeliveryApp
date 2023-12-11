package com.selin.fooddeliveryapp.data.model.response

import com.google.gson.annotations.SerializedName

data class CRUDResponse(
    @SerializedName("success")
    val success: Int,
    @SerializedName("message")
    val message: String){
    val isSuccess: Boolean
        get() = success == 1
}