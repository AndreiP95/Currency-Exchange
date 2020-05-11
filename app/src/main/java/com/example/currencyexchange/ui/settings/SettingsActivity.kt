package com.example.currencyexchange.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.ActivitySettingsBinding
import com.example.currencyexchange.ui.dashboard.DashboardActivity
import com.example.currencyexchange.utils.BaseActivity
import kotlinx.android.synthetic.main.item_curency_value.view.*

class SettingsActivity : BaseActivity() {

    val settingsViewModel: SettingsViewModel by viewModels()
    private lateinit var binding: ActivitySettingsBinding
    lateinit var timer: String
    lateinit var currency: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.buttonDashboard.textview.text = getString(R.string.dashboard_continue)
        createSpinner(binding.spinnerTimer, 1)
        createSpinner(binding.spinnerCurrency, 2)
        setupInitialValues()
    }

    private fun createSpinner(spinner: Spinner, option: Int) {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, R.layout.support_simple_spinner_dropdown_item,
            when (option) {
                1 -> settingsViewModel.timeList
                else -> settingsViewModel.currencyList
            }
        )
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent?.setSelection(position)
                if (option == 1)
                    timer = settingsViewModel.timeList[position]
                else
                    currency = settingsViewModel.currencyList[position]
            }
        }

    }

    private fun goToDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        this.putRefreshTime(timer.toLong())
        this.putSelectedCurrency(currency)
        finish()
    }

    private fun setupInitialValues() {
        timer = getSelectedRefreshTime().toString()
        binding.spinnerTimer.setSelection(settingsViewModel.timeList.indexOf(timer))
        currency = getBaseCurrency()
        binding.spinnerCurrency.setSelection(settingsViewModel.currencyList.indexOf(currency))

        binding.buttonDashboard.setOnClickListener { goToDashboard() }

    }

}
