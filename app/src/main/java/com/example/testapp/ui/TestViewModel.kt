package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import com.example.testapp.dataModel.TestData
import com.example.testapp.network.TestApiManager
import com.example.testapp.network.TestApiModule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testApiManager: TestApiManager
): ViewModel() {
    // TODO: Implement the ViewModel

    val state: BehaviorSubject<TestState> = BehaviorSubject.create()

    init {
        state.onNext(TestState.Loading)
    }

    fun loadData() {

        testApiManager.getData().enqueue(object : Callback<List<TestData>> {
            override fun onFailure(call: Call<List<TestData>>, t: Throwable) {
                state.onNext(
                    TestState.Error
                )
            }

            override fun onResponse(
                call: Call<List<TestData>>,
                response: Response<List<TestData>>
            ) {
                state.onNext(
                    response.body()?.let {
                        TestState.DataLoaded(it)
                    } ?: TestState.Error
                )
            }
        })
    }

}