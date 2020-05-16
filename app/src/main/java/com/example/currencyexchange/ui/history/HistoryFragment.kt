package com.example.currencyexchange.ui.history

import HistoryDataAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.FragmentHistoryBinding
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import com.example.currencyexchange.utils.BaseFragment
import kotlinx.android.synthetic.main.layout_error.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class HistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val historyViewModel by viewModel<HistoryViewModel>()
    private val historyDataAdapter: HistoryDataAdapter by lazy { HistoryDataAdapter(context) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = HistoryFragmentDirections.actionHistoryFragmentToDashboardFragment()
                NavHostFragment.findNavController(this@HistoryFragment).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        retrieveHistoryData()
    }

    private fun retrieveHistoryData() {
        val historyDataSetObserver = Observer<FxHistoryData> { fxHistoryData ->
            if (fxHistoryData == null) {
                binding.errorView.visibility = View.VISIBLE
            } else {
                binding.errorView.visibility = View.INVISIBLE
                historyDataAdapter.fxHistoryData = fxHistoryData
                historyDataAdapter.notifyDataSetChanged()
            }
        }
        historyViewModel.ratesData.observe(this, historyDataSetObserver)
        refreshData()
    }


    private fun refreshData() {
        historyViewModel.refreshData()
    }

    private fun setupUI() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.graphRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            this.adapter = historyDataAdapter
        }

        binding.errorView.retry_button.setOnClickListener { historyViewModel.refreshData() }
    }

}
