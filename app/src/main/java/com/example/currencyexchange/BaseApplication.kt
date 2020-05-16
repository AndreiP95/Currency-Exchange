package com.example.currencyexchange

import android.app.Application
import com.example.currencyexchange.network.FxNetworkInstance
import com.example.currencyexchange.network.fxdata.CurrenciesClient
import com.example.currencyexchange.network.fxhistory.HistoryClient
import com.example.currencyexchange.ui.dashboard.DashboardViewModel
import com.example.currencyexchange.ui.history.HistoryViewModel
import com.example.currencyexchange.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class BaseApplication : Application() {

    val viewModelsModule = module {
        viewModel { DashboardViewModel() }
        viewModel { HistoryViewModel() }
        viewModel { SettingsViewModel() }
    }

    val clientModule = module {
        single { CurrenciesClient() }
        single { HistoryClient() }
    }

    val networkModule = module {
        single {
            FxNetworkInstance()
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(viewModelsModule, clientModule, networkModule))
        }
    }
}