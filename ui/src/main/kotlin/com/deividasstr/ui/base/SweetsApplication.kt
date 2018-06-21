package com.deividasstr.ui.base

import android.os.Handler
import android.os.StrictMode
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.ui.BuildConfig
import com.deividasstr.ui.base.di.AppComponent
import com.deividasstr.ui.base.di.DaggerAppComponent
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import dagger.android.support.DaggerApplication
import timber.log.Timber

class SweetsApplication : DaggerApplication() {

    val appComponent: AppComponent by lazy { DaggerAppComponent.builder()
        .application(this)
        .network(NetworkModule(BuildConfig.BASE_API_URL))
        .build() }

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
            AndroidDevMetrics.initWith(this);
            Handler().postAtFrontOfQueue(::initStrictMode)
        }
    }

    private fun initStrictMode() {
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build())

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }

    override fun applicationInjector() = appComponent
}