package com.deividasstr.ui.base.framework

import com.deividasstr.ui.base.di.AppComponent
import dagger.android.support.DaggerApplication

abstract class BaseApplication : DaggerApplication() {

    abstract val appComponent: AppComponent

    override fun applicationInjector() = appComponent
}