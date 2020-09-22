package com.example.currencyexchange.dashboard

import com.example.currencyexchange.BaseApplication
import com.example.currencyexchange.network.fxdata.CurrenciesClient
import com.example.currencyexchange.ui.dashboard.DashboardViewModel
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class DashboardModelTest : KoinComponent {

    val client by inject<CurrenciesClient>()
    val dashboardViewModel by inject<DashboardViewModel>()

    // TODO -> Should add more tests

    @Before
    fun initApp() {
        val app = BaseApplication()
        startKoin {
            androidContext(app)
            modules(listOf(app.clientModule, app.networkModule, app.viewModelsModule))
        }
    }

    @Test
    fun dataIsRefreshing() {
        dashboardViewModel.refreshData()
        Mockito.verify(client.fetchAllCurrencies(ArgumentMatchers.any(), ArgumentMatchers.any()))
    }

    @Test
    fun checkCurrencyChanged() {
        assert(dashboardViewModel.getBaseCurrency().equals("EUR"))
        dashboardViewModel.setBaseCurrency("RON")
        assert(dashboardViewModel.getBaseCurrency().equals("RON"))

    }

}