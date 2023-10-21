package com.selin.fooddeliveryapp.data.repo

import com.selin.fooddeliveryapp.data.dataSource.FoodsDataSource
import com.selin.fooddeliveryapp.data.entity.Foods
import com.selin.fooddeliveryapp.data.entity.FoodsCart

class FoodsRepo(var fds: FoodsDataSource) {
    suspend fun getAllFoods() : List<Foods> = fds.getAllFoods()

    suspend fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet: Int,kullanici_adi: String) = fds.addFoodToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

    suspend fun getCartFoods(kullanici_adi: String) : List<FoodsCart> = fds.getCartFoods(kullanici_adi)

    suspend fun deleteFoodFromCart(sepet_yemek_id: String, kullanici_adi: String) = fds.deleteFoodFromCart(sepet_yemek_id,kullanici_adi)


}