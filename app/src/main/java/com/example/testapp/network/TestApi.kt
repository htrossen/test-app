package com.example.testapp.network

import com.example.testapp.dataModel.TestData
import io.reactivex.Observable
import retrofit2.http.GET

interface TestApi {

    @GET("posts")
    fun getData(): Observable<List<TestData>>

}