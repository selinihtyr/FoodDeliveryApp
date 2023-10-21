package com.selin.fooddeliveryapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodsCartResponse(@SerializedName("sepet_yemekler")
                             var foods: List<FoodsCart>,
                             var success: Int) {
}