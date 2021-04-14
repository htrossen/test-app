package com.example.testapp.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(): ViewModel() {

    fun loadData() {
        // TODO
    }
}