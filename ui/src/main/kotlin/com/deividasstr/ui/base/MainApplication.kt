package com.deividasstr.ui.base

import android.os.StrictMode
import com.deividasstr.ui.BuildConfig
import com.deividasstr.ui.base.di.DaggerAppComponent
import com.deividasstr.ui.base.di.modules.NetworkModule
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import dagger.android.support.DaggerApplication
import timber.log.Timber

class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }

        AndroidThreeTen.init(this)
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)

            Timber.plant(Timber.DebugTree())

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .detectCustomSlowCalls()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    override fun applicationInjector() = DaggerAppComponent.builder()
            .application(this)
            .network(NetworkModule(BuildConfig.BASE_API_URL))
            .build()
}