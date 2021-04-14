package com.example.testapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.testapp.databinding.TestFragmentBinding
import com.example.testapp.utils.AutoClearOnDestroyProperty
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class TestFragment : TestActionCallback, Fragment() {

    private val disposables = CompositeDisposable()

    private val viewModel: TestViewModel by viewModels()

    private lateinit var adapter: TestAdapter

    private var binding: TestFragmentBinding by AutoClearOnDestroyProperty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TestFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposables.add(
            viewModel
                .state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        renderState(it)
                    },
                    {
                        Log.d(
                            TAG, "Error observing state.", it
                        )
                    }
                )
        )

        adapter = TestAdapter(this)

        binding.list.adapter = adapter

        binding.errorButton.setOnClickListener {
            displayState(showLoading = true)
            viewModel.loadData()
        }
    }

    private fun renderState(state: TestState) {
        when (state) {
            is TestState.DataLoaded -> {
                adapter.update(state.elements)
                displayState(showData = true)
            }
            is TestState.Loading -> displayState(showLoading = true)
            is TestState.Error -> displayState(showError = true)
        }
    }

    private fun displayState(
       showData: Boolean = false,
       showLoading: Boolean = false,
       showError: Boolean = false,
    ) {
        binding.data.isVisible = showData
        binding.loading.isVisible = showLoading
        binding.error.isVisible = showError
    }

    override fun itemClicked(id: String) {
        // TODO
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    companion object {
        private const val TAG = "TestFragment"

        fun newInstance() = TestFragment()
    }
}