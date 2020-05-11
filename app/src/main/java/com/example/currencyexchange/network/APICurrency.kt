package com.example.currencyexchange.network

import com.example.currencyexchange.utils.URIConstants
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APICurrency {

    @GET(URIConstants.LATEST)
    fun getAllCurrencies(@Query("base") base: String? = null): Observable<Response<ResponseBody>>

    @GET(URIConstants.HISTORY)
    fun getHistoryForCurrencies(
        @Query("start_at") startDate: String,
        @Query("end_at") endDate: String,
        @Query("symbols", encoded = true) currencies: String
    ): Observable<Response<ResponseBody>>
}