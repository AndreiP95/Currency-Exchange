package com.example.currencyexchange.ui.settings

import androidx.lifecycle.ViewModel
import com.example.currencyexchange.utils.SettingsConstants

class SettingsViewModel : ViewModel() {

    val timeList: MutableList<String> = SettingsConstants.timeList
    val currencyList: MutableList<String> = SettingsConstants.currencyList

}