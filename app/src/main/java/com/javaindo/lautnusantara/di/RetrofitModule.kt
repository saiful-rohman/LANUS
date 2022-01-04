package com.javaindo.lautnusantara.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.javaindo.lautnusantara.retrofit.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val baseUrl = "https://open-api.xyz/placeholder/"

    @Singleton
    @Provides
    fun provideGsonBuilder() : Gson{
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit.Builder{
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideAPIRetrofit(retrofit: Retrofit.Builder) : API{
        return retrofit.build().create(API::class.java)
    }

}