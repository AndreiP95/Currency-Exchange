package com.example.currencyexchange.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.ActivityDashboardBinding
import com.example.currencyexchange.network.fxdata.model.FxData
import com.example.currencyexchange.ui.dashboard.adapter.CurrenciesAdapter
import com.example.currencyexchange.ui.history.HistoryActivity
import com.example.currencyexchange.ui.settings.SettingsActivity
import com.example.currencyexchange.utils.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : BaseActivity() {

    private val dashboardViewModel by viewModel<DashboardViewModel>()
    lateinit var adapter: CurrenciesAdapter

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        setupUI()
        retrieveAndUpdateData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar, menu)
        return true
    }


    override fun onResume() {
        dashboardViewModel.initTimer(getSelectedRefreshTime())
        dashboardViewModel.setBaseCurrency(getBaseCurrency())
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_item_one) {
            dashboardViewModel.stopTimer()
            startActivity(Intent(this, HistoryActivity::class.java))
        } else if (id == R.id.action_item_two) {
            dashboardViewModel.stopTimer()
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI() {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.currencyRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            this.adapter = adapter
        }

        binding.swipeRefreshCurrency.let {
            it.setOnRefreshListener {
                it.isRefreshing = true
                refreshData()
            }
        }

    }

    private fun refreshData() {
        dashboardViewModel.refreshData()
    }

    private fun retrieveAndUpdateData() {
        val fxDataObserver = Observer<FxData> { fxData ->
            if (!::adapter.isInitialized) {
                adapter = CurrenciesAdapter()
                binding.currencyRecyclerView.adapter = adapter
            }
            if (fxData == null) {
                binding.errorView.visibility = View.VISIBLE
            } else {
                binding.updatedAtText?.text = setTimeStamp()
                binding.swipeRefreshCurrency.isRefreshing = false
                adapter.updateBaseCurrency(dashboardViewModel.getBaseCurrency())
                adapter.updateRatesList(fxData)
                adapter.notifyDataSetChanged()
                binding.errorView.visibility = View.GONE
            }

        }
        dashboardViewModel.currencyList.observe(this, fxDataObserver)
    }


    private fun setTimeStamp(): String {
        return "Last update at ${SimpleDateFormat(
            "HH:mm:ss, dd MMMM yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().timeInMillis)
        }"
    }
}

