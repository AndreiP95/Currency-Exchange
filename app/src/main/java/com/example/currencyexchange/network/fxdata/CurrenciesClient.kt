package com.example.currencyexchange.network.fxdata

import androidx.lifecycle.MutableLiveData
import com.example.currencyexchange.network.FxNetworkInstance
import com.example.currencyexchange.network.fxdata.model.FxData
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class CurrenciesClient : KoinComponent {

    private val networkInstance by inject<FxNetworkInstance>()

    fun fetchAllCurrencies(currency: String, currencyList: MutableLiveData<FxData>) {
        val result = networkInstance.getService().getAllCurrencies(currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ setCurrenciesData(it, currencyList) },
                { currencyList.postValue(null) }
            )

    }

    private fun setCurrenciesData(
        response: Response<ResponseBody>,
        currencyList: MutableLiveData<FxData>
    ) {
        if (response.code() in IntRange(200, 299)) {
            response.body()?.let {
                currencyList.postValue(Gson().fromJson(it.string(), FxData::class.java))
            }
        } else {
            currencyList.postValue(null)
        }
    }

}