package com.example.testapp.ui

sealed class TestState {
    data class DataLoaded(val elements: List<String>) : TestState()
    object Error : TestState()
    object Loading : TestState()
}