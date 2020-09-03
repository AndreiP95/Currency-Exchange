package com.example.currencyexchange.ui.dashboard

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.FragmentDashboardBinding
import com.example.currencyexchange.network.fxdata.model.FxData
import com.example.currencyexchange.ui.dashboard.adapter.CurrenciesAdapter
import com.example.currencyexchange.utils.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : BaseFragment() {

    private val dashboardViewModel by viewModel<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding
    lateinit var adapter: CurrenciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard, container, false
        )
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupUI()
        retrieveAndUpdateData()
        dashboardViewModel.initTimer(getSelectedRefreshTime())
        dashboardViewModel.setBaseCurrency(getBaseCurrency())
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.appbar, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lateinit var action: NavDirections
        val id = item.itemId
        if (id == R.id.action_item_one) {
            dashboardViewModel.stopTimer()
            action = DashboardFragmentDirections.actionDashboardFragmentToHistoryFragment()
            findNavController(this).navigate(action)
        } else if (id == R.id.action_item_two) {
            dashboardViewModel.stopTimer()
            action = DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment()
            findNavController(this).navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupUI() {
        val linearLayoutManager = LinearLayoutManager(activity)
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
        dashboardViewModel.currencyList.observe(viewLifecycleOwner, fxDataObserver)
    }


    private fun setTimeStamp(): String {
        return "Last update at ${SimpleDateFormat(
            "HH:mm:ss, dd MMMM yyyy",
            Locale.getDefault()
        ).format(Calendar.getInstance().timeInMillis)
        }"
    }

}
