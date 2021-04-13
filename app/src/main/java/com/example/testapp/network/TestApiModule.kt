package com.example.testapp.network

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

@Module
@InstallIn(SingletonComponent::class)
object TestApiModule {

    @Reusable
    @Provides
    fun provideApi() : TestApi {

        val flipperClient = AndroidFlipperClient.getInstanceIfInitialized()

        val okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperClient?.getPlugin(NetworkFlipperPlugin.ID)))
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestApi::class.java)
    }
}