package com.papageorgiouk.curfnsurf

import android.app.Application
import com.papageorgiouk.curfnsurf.data.dataModule
import com.papageorgiouk.curfnsurf.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
        }
    }
}