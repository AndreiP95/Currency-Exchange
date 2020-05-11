package com.example.currencyexchange.network

import com.example.currencyexchange.utils.URIConstants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FxNetworkInstance {

    init {
        createRetrofitInstance()
    }

    private val apiCurrency: APICurrency = createRetrofitInstance().create(APICurrency::class.java)

    fun getService(): APICurrency {
        return apiCurrency
    }

    private fun createRetrofitInstance(): Retrofit {

        val httpClient = initOkHttp()

        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(URIConstants.BASE_URI)
            .build()
    }

    private fun initOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    }

}