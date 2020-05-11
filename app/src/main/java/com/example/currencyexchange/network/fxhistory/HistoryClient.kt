package com.example.currencyexchange.network.fxhistory

import androidx.lifecycle.MutableLiveData
import com.example.currencyexchange.network.FxNetworkInstance
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class HistoryClient : KoinComponent {

    private val networkInstance by inject<FxNetworkInstance>()

    fun fetchHistoryData(
        currencies: String,
        startDate: String,
        endDate: String,
        ratesData: MutableLiveData<FxHistoryData>
    ) {
        val result =
            networkInstance.getService().getHistoryForCurrencies(startDate, endDate, currencies)
                .subscribeOn(Schedulers.io())
                .onErrorReturn { ratesData.postValue(null); null }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ setHistoryData(it, ratesData) },
                    { ratesData.postValue(null) }
                )
    }

    private fun setHistoryData(
        response: Response<ResponseBody>,
        ratesData: MutableLiveData<FxHistoryData>
    ) {
        if (response.code() in IntRange(200, 299)) {
            response.body()?.let {
                ratesData.postValue(
                    Gson().fromJson(
                        it.string(),
                        FxHistoryData::class.java
                    )
                )
            }
        } else {
            ratesData.postValue(null)
        }
    }

    private fun onError() {

    }
}