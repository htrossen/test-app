package com.example.testapp.network

import com.example.testapp.dataModel.TestData
import retrofit2.Call
import javax.inject.Inject

class TestApiManager @Inject constructor(
    private val testApi: TestApi
) {
    fun getData(): Call<List<TestData>> {
        return testApi.getData()
    }
}