package com.example.currencyexchange.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyexchange.utils.DataConstants.Companion.BASE_CURRENCY_KEY
import com.example.currencyexchange.utils.DataConstants.Companion.DEFAULT_CURRENCY
import com.example.currencyexchange.utils.DataConstants.Companion.DEFAULT_TIME
import com.example.currencyexchange.utils.DataConstants.Companion.REFRESH_TIMER_KEY
import com.example.currencyexchange.utils.FileConstants.Companion.USER_FILE

open class BaseActivity : AppCompatActivity() {

    private fun getSharedPreferences(): SharedPreferences =
        this.getSharedPreferences(USER_FILE, Context.MODE_PRIVATE)

    fun getSelectedRefreshTime(): Long {
        return getSharedPreferences().getLong(REFRESH_TIMER_KEY, DEFAULT_TIME)
    }

    fun putRefreshTime(refreshTimer: Long) {
        getSharedPreferences().edit().putLong(REFRESH_TIMER_KEY, refreshTimer * 1000).apply()
    }

    fun getBaseCurrency(): String {
        getSharedPreferences().getString(BASE_CURRENCY_KEY, DEFAULT_CURRENCY)?.let {
            return it
        }
        return DEFAULT_CURRENCY
    }

    fun putSelectedCurrency(baseCurrency: String?) {
        getSharedPreferences().edit().putString(BASE_CURRENCY_KEY, baseCurrency).apply()
    }

}
