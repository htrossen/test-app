package com.example.testapp.ui

typealias TestActionHandler = (TestAction) -> Unit

sealed class TestAction {
    data class ItemClicked(val id: String) : TestAction()
}