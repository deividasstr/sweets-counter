package com.deividasstr.utils.di

import android.app.Application
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.ui.base.di.modules.ActivityModule
import com.deividasstr.ui.base.di.modules.FragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (TestAppModule::class),
    (ActivityModule::class),
    (FragmentModule::class)
])

interface TestAppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun network(networkModule: NetworkModule): Builder
        fun db(testDbModule: TestDbModule): Builder
        fun build(): TestAppComponent
    }

    override fun inject(instance: DaggerApplication)
}