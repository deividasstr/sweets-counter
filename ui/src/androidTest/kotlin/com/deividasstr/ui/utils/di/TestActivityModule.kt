package com.deividasstr.ui.utils.di

import com.deividasstr.ui.base.di.modules.FragmentModule
import com.deividasstr.ui.base.di.scopes.ActivityScope
import com.deividasstr.ui.features.main.MainActivity
import com.deividasstr.ui.utils.TestActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class TestActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    @ActivityScope
    abstract fun testActivity(): TestActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    @ActivityScope
    abstract fun mainActivity(): MainActivity
}
