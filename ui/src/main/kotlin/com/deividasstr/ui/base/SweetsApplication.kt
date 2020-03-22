package com.deividasstr.ui.base

import android.os.Handler
import android.os.StrictMode
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.ui.BuildConfig
import com.deividasstr.ui.base.di.AppComponent
import com.deividasstr.ui.base.di.DaggerAppComponent
import com.deividasstr.ui.base.framework.base.BaseApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import javax.inject.Inject
import timber.log.Timber

class SweetsApplication : BaseApplication() {

    @Inject lateinit var workersFactory: WorkerFactory

    override val appComponent: AppComponent by lazy { DaggerAppComponent.builder()
        .application(this)
        .network(NetworkModule(BuildConfig.BASE_API_URL))
        .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        appComponent.inject(this)

        AndroidThreeTen.init(this)
        initWorkManager()

        if (BuildConfig.DEBUG) {
            LeakCanary.install(this)
            Timber.plant(Timber.DebugTree())
            Handler().postAtFrontOfQueue(::initStrictMode)
            initStrictMode()
        }
    }

    private fun initWorkManager() {
        val configuration = Configuration.Builder()
            .setWorkerFactory(workersFactory)
            .build()
        WorkManager.initialize(applicationContext, configuration)
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
}
