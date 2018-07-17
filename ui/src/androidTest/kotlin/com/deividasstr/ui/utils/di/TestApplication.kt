package com.deividasstr.ui.utils.di

import com.deividasstr.ui.base.SweetsApplication
import com.jakewharton.threetenabp.AndroidThreeTen

class TestApplication : SweetsApplication() {

    override lateinit var appComponent: TestAppComponent
        /*DaggerTestAppComponent.builder()
            .application(this)
            .dbModule(TestDbModule())
            .networkModule(TestNetworkModule("https://hello.world.com/api/"))
            .sharedPrefsModule(TestSharedPrefsModule())
            //.useCaseModule()
            .build()*/

    override fun onCreate() {
        //super.onCreate()
        AndroidThreeTen.init(this)
    }

    fun setComponent(component: TestAppComponent) {
        appComponent = component
        super.onCreate()
    }

    //override fun applicationInjector(): TestAppComponent = appComponent
}