package com.deividasstr.ui.utils.di

import com.deividasstr.ui.base.framework.base.BaseApplication
import com.jakewharton.threetenabp.AndroidThreeTen

open class TestApplication : BaseApplication() {

    override lateinit var appComponent: TestAppComponent

    override fun onCreate() {
        AndroidThreeTen.init(this)
    }

    fun setComponent(component: TestAppComponent) {
        appComponent = component
        super.onCreate()
    }
}