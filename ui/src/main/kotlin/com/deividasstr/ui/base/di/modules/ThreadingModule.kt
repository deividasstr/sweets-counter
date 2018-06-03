package com.deividasstr.ui.base.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class ThreadingModule {

    companion object {
        const val MAIN_SCHEDULER = "main_scheduler"
        const val BACKGROUND_SCHEDULER = "background_scheduler"
    }

    @Provides
    @Singleton
    @Named(ThreadingModule.MAIN_SCHEDULER)
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    @Named(ThreadingModule.BACKGROUND_SCHEDULER)
    fun provideBackgroundScheduler(): Scheduler {
        return Schedulers.io()
    }
}