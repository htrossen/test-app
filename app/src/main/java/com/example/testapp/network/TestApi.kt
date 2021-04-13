package com.example.testapp.network

import com.example.testapp.dataModel.TestData
import retrofit2.Call
import retrofit2.http.GET

interface TestApi {

    @GET("posts")
    fun getData(): Call<List<TestData>>

}