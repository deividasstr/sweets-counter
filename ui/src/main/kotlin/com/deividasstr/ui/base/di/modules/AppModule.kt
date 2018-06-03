package com.deividasstr.ui.base.di.modules

import android.app.Application
import android.content.Context
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
        (ViewModelModule::class)
    ]
)
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}