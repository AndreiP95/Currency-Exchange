package com.example.currencyexchange.ui.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    val timeList: MutableList<String> = mutableListOf("3", "5", "15")
    val currencyList: MutableList<String> = mutableListOf("EUR", "USD", "BGN", "RON")

}