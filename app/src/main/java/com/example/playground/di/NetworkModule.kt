package com.example.playground.di

import com.example.playground.data.api.APIEndpoints
import com.example.playground.data.api.ApiDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ApiDetails.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesApiEndpoints(retrofit: Retrofit): APIEndpoints {
        return retrofit.create(APIEndpoints::class.java)
    }
}