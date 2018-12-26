package com.deividasstr.ui.base.di

import android.app.Application
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.ui.base.SweetsApplication
import com.deividasstr.ui.base.di.modules.ActivityModule
import com.deividasstr.ui.base.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AndroidSupportInjectionModule::class),
    (AppModule::class),
    (ActivityModule::class)
])

interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun network(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
    fun inject(instance: SweetsApplication)
}