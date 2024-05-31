package com.tangerine.taipeitour

import android.app.Application
import com.tangerine.core.api.apiModule
import com.tangerine.taipeitour.views.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TaipeiTourApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Dependency Injection Initialization via Koin.
         */
        startKoin {
            androidContext(this@TaipeiTourApplication)
            androidLogger()
            modules(getAllModules().map { it })
        }
    }

    private fun getAllModules() = listOf(apiModule, mainModule)
}
