package com.deividasstr.ui.utils.di

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.support.DaggerApplication

class TestApplication : DaggerApplication() {

    val appComponent: TestAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .application(this)
            .dbModule(TestDbModule())
            .networkModule(TestNetworkModule())
            .sharedPrefsModule(TestSharedPrefsModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector() = appComponent
}