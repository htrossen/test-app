package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import com.example.testapp.dataModel.TestData
import com.example.testapp.network.TestApiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val testApiManager: TestApiManager
): ViewModel() {

    val state: BehaviorSubject<TestState> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()

    init {
        state.onNext(TestState.Loading)
    }

    fun loadData() {
        compositeDisposable.add(testApiManager.getData()
            .subscribeOn(Schedulers.io())
            .subscribe(::handleResults, ::handleError)
        )
    }

    private fun handleResults(items: List<TestData>) {
        val elements = mutableListOf<TestListCell>()
        items.forEach {
            elements.add(TestListCell.Item(it))
            elements.add(TestListCell.Divider)
        }
        state.onNext(TestState.DataLoaded(items, elements))
    }

    private fun handleError(t: Throwable) {
        state.onNext(TestState.Error)
    }
}