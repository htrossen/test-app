package com.example.testapp.network

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO
//@Module
//@InstallIn(SingletonComponent::class)
//object TestApiModule {
//
//    @Reusable
//    @Provides
//    fun provideApi() : TestApi {}
//}