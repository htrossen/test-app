package com.example.testapp.ui

import com.example.testapp.dataModel.TestData;

sealed class TestState {
    data class DataLoaded(val elements: List<TestListCell>) : TestState()
    object Error : TestState()
    object Loading : TestState()
}