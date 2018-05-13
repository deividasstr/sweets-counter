package com.deividasstr

import android.app.Application
import android.os.StrictMode
import com.deividasstr.splash.BuildConfig
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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