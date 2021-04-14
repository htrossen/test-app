package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import com.example.testapp.network.TestApiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testApiManager: TestApiManager
): ViewModel() {

    val state: BehaviorSubject<TestState> = BehaviorSubject.create()

    init {
        state.onNext(TestState.Loading)
    }

    fun loadData() {
        // TODO -- enqueue
    }
}