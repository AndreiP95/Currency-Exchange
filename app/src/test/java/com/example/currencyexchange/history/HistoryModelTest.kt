package com.example.currencyexchange.history

import com.example.currencyexchange.BaseApplication
import com.example.currencyexchange.network.fxhistory.HistoryClient
import com.example.currencyexchange.ui.history.HistoryViewModel
import org.junit.BeforeClass
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.mockito.Mockito

class HistoryModelTest : KoinComponent {

    val client by inject<HistoryClient>()

    @Test
    fun timeIsNotInitializedByDefault() {
        val mockViewModel = Mockito.spy(HistoryViewModel())
        Mockito.verify(mockViewModel, Mockito.never()).getCurrentTime()
    }

    companion object {
        @BeforeClass
        fun initApp() {
            val app = BaseApplication()
            startKoin {
                androidContext(app)
                modules(listOf(app.clientModule, app.networkModule, app.viewModelsModule))
            }
        }
    }

}