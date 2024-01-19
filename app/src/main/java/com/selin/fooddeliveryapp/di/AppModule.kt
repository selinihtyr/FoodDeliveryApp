package com.selin.fooddeliveryapp.di

import android.content.Context
import androidx.room.Room
import com.selin.fooddeliveryapp.data.source.locale.Database
import com.selin.fooddeliveryapp.data.source.locale.FavoriteDao
import com.selin.fooddeliveryapp.data.source.remote.FoodApi
import com.selin.fooddeliveryapp.data.repo.FoodRepo
import com.selin.fooddeliveryapp.utils.constans.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodRepository(service: FoodApi, favoriteDao: FavoriteDao): FoodRepo {
        return FoodRepo(service, favoriteDao)
    }

    @Provides
    @Singleton
    fun provideFood(): FoodApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FoodApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(@ApplicationContext context: Context): FavoriteDao {
        return Room.databaseBuilder(context, Database::class.java, "favorites")
            .build()
            .getFavoritesDao()
    }
}