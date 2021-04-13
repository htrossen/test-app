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
class TestFragment : Fragment() {

    private val disposables = CompositeDisposable()

    private val viewModel: TestViewModel by viewModels()

    private val controller = TestController()

    private var binding: TestFragmentBinding by AutoClearOnDestroyProperty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
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

        // TODO: Subscribe to observables
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

        binding.list.adapter = controller.adapter

        binding.errorButton.setOnClickListener {
            displayState(showLoading = true)
            viewModel.loadData()
        }
    }

    private fun renderState(state: TestState) {
        when (state) {
            is TestState.DataLoaded -> {
                controller.setData(state.data, ::handleAction)
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

    private fun handleAction(action: TestAction) {
        when (action) {
            // TODO
            is TestAction.ItemClicked -> {}
        }
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