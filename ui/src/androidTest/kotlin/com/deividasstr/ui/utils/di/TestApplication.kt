package com.deividasstr.ui.utils.di

import com.deividasstr.ui.base.SweetsApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import okhttp3.HttpUrl

class TestApplication : SweetsApplication() {

    override val appComponent: TestAppComponent by lazy {
        DaggerTestAppComponent.builder()
            .application(this)
            .dbModule(TestDbModule())
            .networkModule(TestNetworkModule("https://hello.world.com/api/"))
            .sharedPrefsModule(TestSharedPrefsModule())
            .build()
    }

    lateinit var mockUrl: HttpUrl

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    //override fun applicationInjector() = appComponent
}