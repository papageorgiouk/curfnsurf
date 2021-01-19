package com.papageorgiouk.curfnsurf

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.papageorgiouk.curfnsurf.data.dataModule
import com.papageorgiouk.curfnsurf.domain.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCenter.start(this, getString(R.string.app_center_secret), Analytics::class.java, Crashes::class.java)

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
        }
    }
}