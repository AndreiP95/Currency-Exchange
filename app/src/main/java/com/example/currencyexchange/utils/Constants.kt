package com.example.currencyexchange.utils

class URIConstants {

    companion object {
        const val BASE_URI = "https://api.exchangeratesapi.io/"
        const val LATEST = "latest"
        const val HISTORY = "history"

    }
}

class FileConstants {
    companion object {
        const val USER_FILE = "user_settings"
    }
}

class DataConstants {
    companion object {
        const val DEFAULT_TIME = 3000.toLong()
        const val DEFAULT_CURRENCY = "EUR"
        val CURRENCY_LIST = listOf("RON", "BGN", "USD")
        val CURRENCY_PARAMETERS = CURRENCY_LIST.fold("") { acc, element -> acc + "$element," }
            .removeSuffix(",")
        val REFRESH_TIMER_KEY = "refresh_timer"
        val BASE_CURRENCY_KEY = "base_currency"

    }
}

class SettingsConstants {
    companion object {
        val timeList = mutableListOf("3", "5", "15")
        val currencyList = mutableListOf("EUR", "USD", "BGN", "RON")
    }
}

class CalendarConstants {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val MILISECONDS_IN_ONE_DAY = 86400000
    }
}