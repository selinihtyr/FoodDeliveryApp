package com.selin.fooddeliveryapp.data.remote

import com.selin.fooddeliveryapp.data.model.response.CRUDResponse
import com.selin.fooddeliveryapp.data.model.response.FoodCartResponse
import com.selin.fooddeliveryapp.data.model.response.FoodResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    //http://kasimadalan.pe.hu/yemekler/sepettekiYemekleriGetir.php
    //http://kasimadalan.pe.hu/yemekler/sepeteYemekEkle.php
    //http://kasimadalan.pe.hu/yemekler/sepettenYemekSil.php
    //http://kasimadalan.pe.hu/yemekler/resimler/ayran.png
    //BASE URL -> http://kasimadalan.pe.hu/
    //API URL -> yemekler/tumYemekleriGetir.php
    //API URL -> yemekler/sepettekiYemekleriGetir.php
    //API URL -> yemekler/sepeteYemekEkle.php
    //API URL -> yemekler/sepettenYemekSil.php
    //API URL -> yemekler/resimler/ayran.png

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodResponse


    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFoodToCart(
        @Field("yemek_adi") foodName: String,
        @Field("yemek_resim_adi") foodImageName: String,
        @Field("yemek_fiyat") foodPrice: Int,
        @Field("yemek_siparis_adet") foodOrderQuantity: Int,
        @Field("kullanici_adi") username: String
    ): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getCartFoods(@Field("kullanici_adi") username: String): Call<FoodCartResponse>


    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodFromCart(
        @Field("sepet_yemek_id") cartFoodId: Int,
        @Field("kullanici_adi") username: String
    ): CRUDResponse

}