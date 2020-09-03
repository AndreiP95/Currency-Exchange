package com.example.currencyexchange.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private val sharedPrefs: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            FileConstants.USER_FILE,
            Context.MODE_PRIVATE
        )
    }

    fun getSelectedRefreshTime(): Long {
        return sharedPrefs.getLong(
            DataConstants.REFRESH_TIMER_KEY,
            DataConstants.DEFAULT_TIME
        )
    }

    fun putRefreshTime(refreshTimer: Long) {
        sharedPrefs.edit().putLong(DataConstants.REFRESH_TIMER_KEY, refreshTimer * 1000).apply()
    }

    fun getBaseCurrency(): String {
        sharedPrefs.getString(
            DataConstants.BASE_CURRENCY_KEY,
            DataConstants.DEFAULT_CURRENCY
        )?.let {
            return it
        }
        return DataConstants.DEFAULT_CURRENCY
    }

    fun putSelectedCurrency(baseCurrency: String?) {
        sharedPrefs.edit().putString(DataConstants.BASE_CURRENCY_KEY, baseCurrency).apply()
    }

}