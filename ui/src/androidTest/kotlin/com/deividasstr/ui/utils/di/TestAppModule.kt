package com.deividasstr.ui.utils.di

import android.app.Application
import android.content.Context
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.di.modules.RepoModule
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.ui.base.di.modules.ThreadingModule
import com.deividasstr.ui.base.di.modules.UseCaseModule
import com.deividasstr.ui.base.di.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [(ThreadingModule::class),
        (DbModule::class),
        (NetworkModule::class),
        (RepoModule::class),
        (UseCaseModule::class),
        (ViewModelModule::class),
        (SharedPrefsModule::class)
    ]
)
class TestAppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}