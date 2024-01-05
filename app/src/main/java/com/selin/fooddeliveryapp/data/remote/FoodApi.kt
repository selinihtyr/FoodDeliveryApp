package com.selin.fooddeliveryapp.data.remote

import com.selin.fooddeliveryapp.data.model.response.CRUDResponse
import com.selin.fooddeliveryapp.data.model.response.FoodCartListResponse
import com.selin.fooddeliveryapp.data.model.response.FoodListResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodApi {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodListResponse


    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addFoodToCart(
        @Field("yemek_adi") name: String,
        @Field("yemek_resim_adi") imageName: String,
        @Field("yemek_fiyat") price: Int,
        @Field("yemek_siparis_adet") orderQuantity: Int,
        @Field("kullanici_adi") username: String
    ): CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getCartFoods(@Field("kullanici_adi") username: String): Call<FoodCartListResponse>


    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteFoodFromCart(
        @Field("sepet_yemek_id") cartFoodId: Int,
        @Field("kullanici_adi") username: String
    ): CRUDResponse

}