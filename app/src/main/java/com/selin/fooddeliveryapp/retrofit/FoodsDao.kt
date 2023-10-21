package com.selin.fooddeliveryapp.retrofit

import com.selin.fooddeliveryapp.data.entity.CRUDResponse
import com.selin.fooddeliveryapp.data.entity.FoodsCartResponse
import com.selin.fooddeliveryapp.data.entity.FoodsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {
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
    suspend fun getAllFoods() : FoodsResponse


    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFoodToCart(@Field("yemek_adi") yemek_adi: String,
                              @Field("yemek_resim_adi") yemek_resim_adi: String,
                              @Field("yemek_fiyat") yemek_fiyat: Int,
                              @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
                              @Field("kullanici_adi") kullanici_adi: String) : CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getCartFoods(@Field("kullanici_adi") kullanici_adi: String) : Call<FoodsCartResponse>



    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodFromCart(@Field("sepet_yemek_id") sepet_yemek_id: String,
                                   @Field("kullanici_adi") kullanici_adi: String) : CRUDResponse

}