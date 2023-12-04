package com.selin.fooddeliveryapp.retrofit

import com.selin.fooddeliveryapp.data.remote.FoodApi

class ApiUtils {
    companion object {
        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getFoodsApi(): FoodApi {
            return RetrofitClient.getClient(BASE_URL).create(FoodApi::class.java)
        }
    }
}