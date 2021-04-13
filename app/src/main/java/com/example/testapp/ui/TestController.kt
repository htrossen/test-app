package com.example.testapp.ui

import com.airbnb.epoxy.Typed2EpoxyController
import com.example.testapp.dataModel.TestData
import com.example.testapp.ui.epoxyModel.divider
import com.example.testapp.ui.epoxyModel.testItem

class TestController : Typed2EpoxyController<List<TestData>, TestActionHandler>() {

    override fun buildModels(
        testDataItems: List<TestData>,
        actionHandler: TestActionHandler
    ) {
        testDataItems.forEach { item ->
            testItem {
                id(item.id)
                item(item)
                actionHandler(actionHandler)
            }
            divider {
                id("divider${item.id}")
            }
        }
    }
}