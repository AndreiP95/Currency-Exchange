package com.example.currencyexchange.ui.history

import HistoryDataAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.databinding.ActivityHistoryBinding
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import kotlinx.android.synthetic.main.layout_error.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private val historyViewModel by viewModel<HistoryViewModel>()
    private lateinit var binding: ActivityHistoryBinding
    private val historyDataAdapter: HistoryDataAdapter by lazy { HistoryDataAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


    fun refreshData() {
        historyViewModel.refreshData()
    }

    private fun setupUI() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.graphRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            this.adapter = historyDataAdapter
        }

        binding.errorView.retry_button.setOnClickListener { historyViewModel.refreshData() }
    }

}