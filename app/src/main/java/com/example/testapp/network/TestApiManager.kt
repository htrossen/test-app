package com.example.testapp.network

import com.example.testapp.dataModel.TestData
import io.reactivex.Observable
import javax.inject.Inject

class TestApiManager @Inject constructor(
    private val testApi: TestApi
) {
    fun getData(): Observable<List<TestData>> {
        return testApi.getData()
    }
}