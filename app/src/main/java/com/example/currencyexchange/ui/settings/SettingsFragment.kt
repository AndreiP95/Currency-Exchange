package com.example.currencyexchange.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.FragmentSettingsBinding
import com.example.currencyexchange.utils.BaseFragment
import kotlinx.android.synthetic.main.item_curency_value.view.*

class SettingsFragment : BaseFragment() {

    lateinit var binding: FragmentSettingsBinding
    val settingsViewModel: SettingsViewModel by viewModels()
    lateinit var timer: String
    lateinit var currency: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SettingsFragmentDirections.actionSettingsFragmentToDashboardFragment()
                NavHostFragment.findNavController(this@SettingsFragment).navigate(action)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.buttonDashboard.textview.text = getString(R.string.dashboard_continue)
        createSpinner(binding.spinnerTimer, 1)
        createSpinner(binding.spinnerCurrency, 2)
        setupInitialValues()
    }


    private fun createSpinner(spinner: Spinner, option: Int) {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
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
        Log.d("TAG", " $timer $currency")
        this.putRefreshTime(timer.toLong())
        this.putSelectedCurrency(currency)
        val action = SettingsFragmentDirections.actionSettingsFragmentToDashboardFragment()
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun setupInitialValues() {
        timer = getSelectedRefreshTime().div(1000).toString()
        binding.spinnerTimer.setSelection(settingsViewModel.timeList.indexOf(timer))
        currency = getBaseCurrency()
        binding.spinnerCurrency.setSelection(settingsViewModel.currencyList.indexOf(currency))
        binding.buttonDashboard.setOnClickListener { goToDashboard() }

    }
}
