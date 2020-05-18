package com.example.currencyexchange.ui.graph

import androidx.lifecycle.ViewModel
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData

class GraphViewModel : ViewModel() {

    private lateinit var fxHistoryData: FxHistoryData
    private lateinit var currency: String

    fun initHistoryData(fxHistoryData: FxHistoryData) {
        this.fxHistoryData = fxHistoryData
    }

    fun getHistoryData(): FxHistoryData? {
        if (::fxHistoryData.isInitialized)
            return fxHistoryData
        return null
    }

    fun getCurrency(): String = currency

    fun setCurrency(currency: String) {
        this.currency = currency
    }

}