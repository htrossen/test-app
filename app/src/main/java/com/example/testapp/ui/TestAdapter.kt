package com.example.testapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.dataModel.TestData
import com.example.testapp.databinding.DividerBinding
import com.example.testapp.databinding.TestHeaderBinding
import com.example.testapp.databinding.TestItemBinding

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1
private const val ITEM_VIEW_TYPE_DIVIDER = 2


interface TestActionCallback {
    fun itemClicked(id: String)
}

class TestAdapter(private val clickListener: TestActionCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elements = mutableListOf<TestListCell>()

    fun update(newElements: List<TestListCell>) {
        val result = DiffUtil.calculateDiff(TestDiffUtil(elements, newElements))

        elements.clear()
        elements.addAll(newElements)

        result.dispatchUpdatesTo(this)
    }

    class HeaderViewHolder(private val binding: TestHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(title: String) {
            binding.title.text = title
        }
    }

    class ItemViewHolder(private val binding: TestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: TestData, callback: TestActionCallback) {
            binding.header.text = item.header
            binding.message.text = item.message
            binding.itemContainer.setOnClickListener {
                callback.itemClicked(item.id)
            }
        }
    }

    class DividerViewHolder(private val binding: DividerBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> {
                val binding = TestHeaderBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                HeaderViewHolder(binding)
            }
            ITEM_VIEW_TYPE_ITEM -> {
                val binding = TestItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                ItemViewHolder(binding)
            }
            ITEM_VIEW_TYPE_DIVIDER -> {
                val binding = DividerBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                DividerViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elements[position]) {
            is TestListCell.Header -> ITEM_VIEW_TYPE_HEADER
            is TestListCell.Item -> ITEM_VIEW_TYPE_ITEM
            is TestListCell.Divider -> ITEM_VIEW_TYPE_DIVIDER
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is HeaderViewHolder -> viewHolder.bindData((elements[position] as TestListCell.Header).title)
            is ItemViewHolder -> viewHolder.bindData((elements[position] as TestListCell.Item).item, clickListener)
            is DividerViewHolder -> {} // No data to bind
        }
    }

    override fun getItemCount() = elements.size
}

class TestDiffUtil(
    private val oldList: List<TestListCell>,
    private val newList: List<TestListCell>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(oldItemPosition, newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is TestListCell.Item && newItem is TestListCell.Item) {
            oldItem.item.id == newItem.item.id
        } else if (oldItem is TestListCell.Header && newItem is TestListCell.Header) {
            oldItem.title == newItem.title
        } else {
            oldItem is TestListCell.Divider && newItem is TestListCell.Divider
        }
    }
}

sealed class TestListCell {
    data class Item(val item: TestData) : TestListCell()
    data class Header(val title: String) : TestListCell()
    object Divider : TestListCell()
}