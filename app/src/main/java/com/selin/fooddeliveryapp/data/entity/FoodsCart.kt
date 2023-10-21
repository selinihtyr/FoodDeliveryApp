package com.selin.fooddeliveryapp.data.entity

import java.io.Serializable

data class FoodsCart(var sepet_yemek_id: String,
                     var yemek_adi: String,
                     var yemek_resim_adi: String,
                     var yemek_fiyat: Int,
                     var yemek_siparis_adet: Int,
                     var kullanici_adi: String) {
}