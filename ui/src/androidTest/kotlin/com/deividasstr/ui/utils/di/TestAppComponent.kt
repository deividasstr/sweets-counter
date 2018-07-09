package com.deividasstr.ui.utils.di

import android.app.Application
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.ui.base.di.modules.FragmentModule
import com.deividasstr.ui.features.main.MainActivityTest
import com.deividasstr.ui.features.main.MainActivityViewModelTest
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListFragmentTest
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
    (TestActivityModule::class),
    (FragmentModule::class)
])

interface TestAppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun dbModule(dbModule: DbModule): Builder
        //@BindsInstance fun networkModule(networkModule: TestNetworkModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun sharedPrefsModule(sharedPrefsModule: SharedPrefsModule): Builder
        //fun network(networkModule: NetworkModule): Builder
        fun build(): TestAppComponent
    }

    override fun inject(instance: DaggerApplication)
    fun inject(sweetsSearchListFragmentTest: SweetsSearchListFragmentTest)
    fun inject(mainActivityTest: MainActivityTest)
    fun inject(mainActivityViewModelTest: MainActivityViewModelTest)
}