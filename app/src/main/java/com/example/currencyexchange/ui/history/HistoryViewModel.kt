package com.example.currencyexchange.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.network.fxhistory.HistoryClient
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import com.example.currencyexchange.utils.CalendarConstants.Companion.DATE_FORMAT
import com.example.currencyexchange.utils.CalendarConstants.Companion.MILISECONDS_IN_ONE_DAY
import com.example.currencyexchange.utils.DataConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

class HistoryViewModel : ViewModel(), KoinComponent {

    val ratesData: MutableLiveData<FxHistoryData> by lazy { MutableLiveData<FxHistoryData>() }
    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    private val historyClient by inject<HistoryClient>()

    private lateinit var calendar: Calendar
    private lateinit var endDate: String
    private lateinit var startDate: String

    fun getCurrentTime() {
        calendar = Calendar.getInstance()
        endDate = dateFormatter.format(calendar.timeInMillis)
        startDate = dateFormatter.format(calendar.timeInMillis - MILISECONDS_IN_ONE_DAY * 10)
    }

    fun refreshData() {
        getCurrentTime()

        viewModelScope.launch(Dispatchers.IO) {
            historyClient.fetchHistoryData(
                DataConstants.CURRENCY_PARAMETERS, startDate, endDate, ratesData
            )
        }
    }
}