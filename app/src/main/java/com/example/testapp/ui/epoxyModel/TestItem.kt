package com.example.testapp.ui.epoxyModel

import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.testapp.R
import com.example.testapp.ui.TestAction
import com.example.testapp.ui.TestActionHandler
import com.example.testapp.dataModel.TestData
import com.example.testapp.utils.KotlinEpoxyHolder

@EpoxyModelClass
abstract class TestItem : EpoxyModelWithHolder<TestItemViewHolder>() {

    @EpoxyAttribute
    lateinit var item: TestData

    @EpoxyAttribute
    lateinit var actionHandler: TestActionHandler

    override fun getDefaultLayout(): Int = R.layout.test_item

    override fun bind(holder: TestItemViewHolder) {
        super.bind(holder)

        holder.header.text = item.header
        holder.message.text = item.message

        holder.container.setOnClickListener {
            actionHandler(TestAction.ItemClicked(item.id))
        }
    }
}

class TestItemViewHolder : KotlinEpoxyHolder() {
    val container by bind<ConstraintLayout>(R.id.item_container)
    val header by bind<AppCompatTextView>(R.id.header)
    val message by bind<AppCompatTextView>(R.id.message)
}