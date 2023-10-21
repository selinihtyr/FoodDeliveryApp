package com.selin.fooddeliveryapp.di

import com.selin.fooddeliveryapp.data.dataSource.FoodsDataSource
import com.selin.fooddeliveryapp.data.repo.FoodsRepo
import com.selin.fooddeliveryapp.retrofit.ApiUtils
import com.selin.fooddeliveryapp.retrofit.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.http.FormUrlEncoded
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodRepository(fds:FoodsDataSource) : FoodsRepo {
        return FoodsRepo(fds)
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(fdao: FoodsDao) : FoodsDataSource {
        return FoodsDataSource(fdao)
    }

    @Provides
    @Singleton
    fun provideFoodDao() : FoodsDao {
        return ApiUtils.getFoodsDao()
    }
}