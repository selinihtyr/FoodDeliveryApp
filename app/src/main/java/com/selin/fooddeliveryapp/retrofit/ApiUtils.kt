package com.selin.fooddeliveryapp.retrofit

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getFoodsDao(): FoodApi {
            return RetrofitClient.getClient(BASE_URL).create(FoodApi::class.java)
        }
    }
}