package com.selin.fooddeliveryapp.di

import com.selin.fooddeliveryapp.data.remote.FoodApi
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.retrofit.ApiUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodRepository(service: FoodApi): FoodRepo {
        return FoodRepo(service)
    }

    @Provides
    @Singleton
    fun provideFoodDao(): FoodApi {
        return ApiUtils.getFoodsApi()
    }
}