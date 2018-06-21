package com.deividasstr.ui.base.di.modules

import com.deividasstr.ui.base.di.scopes.ActivityScope
import com.deividasstr.ui.features.main.MainActivity
import com.deividasstr.ui.features.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    @ActivityScope
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun splashActivity(): SplashActivity
}