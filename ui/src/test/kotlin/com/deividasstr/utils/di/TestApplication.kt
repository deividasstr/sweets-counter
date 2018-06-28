package com.deividasstr.utils.di

import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.ui.BuildConfig
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.support.DaggerApplication

class TestApplication : DaggerApplication() {

    lateinit var sweetsDb: SweetsDb

    val appComponent: TestAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .application(this)
            .db(TestDbModule(sweetsDb))
            .network(NetworkModule(BuildConfig.BASE_API_URL))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector() = appComponent
}