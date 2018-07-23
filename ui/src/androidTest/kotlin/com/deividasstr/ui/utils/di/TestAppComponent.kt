package com.deividasstr.ui.utils.di

import android.app.Application
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.ui.base.di.AppComponent
import com.deividasstr.ui.base.di.modules.AppModule
import com.deividasstr.ui.base.di.modules.FragmentModule
import com.deividasstr.ui.base.di.modules.UseCaseModule
import com.deividasstr.ui.features.facts.FactsFragmentTest
import com.deividasstr.ui.features.main.MainActivityTest
import com.deividasstr.ui.features.main.backgroundwork.BackgroundWorkManagerTest
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListFragmentTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (TestActivityModule::class),
        (FragmentModule::class)
    ])

interface TestAppComponent : AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun networkModule(networkModule: NetworkModule): Builder
        fun dbModule(dbModule: DbModule): Builder
        fun sharedPrefsModule(sharedPrefsModule: SharedPrefsModule): Builder
        fun useCaseModule(useCaseModule: UseCaseModule): Builder
        fun build(): TestAppComponent
    }

    override fun inject(instance: DaggerApplication)
    fun inject(sweetsSearchListFragmentTest: SweetsSearchListFragmentTest)
    fun inject(mainActivityTest: MainActivityTest)
    fun inject(backgroundWorkManagerTest: BackgroundWorkManagerTest)
    fun inject(factsFragmentTest: FactsFragmentTest)
}