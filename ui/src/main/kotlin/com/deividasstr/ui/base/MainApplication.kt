package com.deividasstr.ui.base

import android.app.Application
import android.os.StrictMode
import com.deividasstr.ui.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .penaltyDeath()
                    .build())
        }
    }
}