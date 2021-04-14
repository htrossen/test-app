package com.example.testapp.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// TODO ITEM_VIEW_TYPE

// TODO
//interface Callbacks {}

class TestAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elements = mutableListOf<String>()

    fun update(newElements: List<String>) {
        // TODO -- CalculateDiff and dispatchUpdates
        elements.clear()
        elements.addAll(newElements)
    }

    // TODO ViewHolders
//    class ItemViewHolder(private val binding: XBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bindData(item: TestData, callback: TestActionCallback) {
//        }
//    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // TODO -- Get binding and pass to ViewHolder
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elements[position]) {
            // TODO -- Map CellTypes to ITEM_VIEW_TYPE
            else -> 0
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            // TODO -- Map ViewHolder to bindData
            else -> {}
        }
    }

    override fun getItemCount() = elements.size
}

// TODO
//class TestDiffUtil(
//    private val oldList: List<>,
//    private val newList: List<>
//) : DiffUtil.Callback() {}

// TODO
//sealed class CellTypes {}