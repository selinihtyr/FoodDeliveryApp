package com.selin.fooddeliveryapp.di

import com.selin.fooddeliveryapp.data.dataSource.FoodDataSource
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.retrofit.ApiUtils
import com.selin.fooddeliveryapp.retrofit.FoodApi
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
    fun provideFoodRepository(foodDataSource: FoodDataSource): FoodRepo {
        return FoodRepo(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(foodApi: FoodApi): FoodDataSource {
        return FoodDataSource(foodApi)
    }

    @Provides
    @Singleton
    fun provideFoodDao(): FoodApi {
        return ApiUtils.getFoodsDao()
    }
}