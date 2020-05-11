package com.example.currencyexchange.ui.dashboard

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchange.network.fxdata.CurrenciesClient
import com.example.currencyexchange.network.fxdata.model.FxData
import com.example.currencyexchange.utils.DataConstants
import org.koin.core.KoinComponent
import org.koin.core.inject

class DashboardViewModel : ViewModel(), KoinComponent {

    val currencyList: MutableLiveData<FxData> by lazy { MutableLiveData<FxData>() }
    private val client = inject<CurrenciesClient>()

    private lateinit var mainHandler: Handler
    private var time: Long = DataConstants.DEFAULT_TIME
    private var baseCurrency: String = DataConstants.DEFAULT_CURRENCY

    fun initTimer(time: Long) {
        this.time = time
        if (!::mainHandler.isInitialized) {
            mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(updateDataTask)
        }
    }

    fun stopTimer() {
        mainHandler.removeCallbacks(updateDataTask)
    }

    fun setBaseCurrency(baseCurrency: String) {
        this.baseCurrency = baseCurrency
    }

    fun getBaseCurrency(): String = baseCurrency

    private val updateDataTask = object : Runnable {
        override fun run() {
            mainHandler.postDelayed(this, time)
            refreshData()
        }
    }

    fun refreshData() {
        client.value.fetchAllCurrencies(baseCurrency, currencyList)
    }

}