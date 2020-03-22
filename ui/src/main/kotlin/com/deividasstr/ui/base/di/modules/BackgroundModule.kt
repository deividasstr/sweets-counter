package com.deividasstr.ui.base.di.modules

import androidx.work.WorkerFactory
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase
import com.deividasstr.ui.features.main.backgroundwork.BackgroundWorkManager
import com.deividasstr.ui.features.main.backgroundwork.WorkersFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BackgroundModule {

    companion object {
        const val MAIN_SCHEDULER = "main_scheduler"
        const val BACKGROUND_SCHEDULER = "background_scheduler"
    }

    /*@Provides
    @Singleton
    @Named(BackgroundModule.BACKGROUND_SCHEDULER)
    fun provideBackgroundScheduler(): Scheduler {
        return Schedulers.io()
    }*/

    @Provides
    @Singleton
    fun provideWorkersFactory(
        downloadAllSweetsUseCase: DownloadAllSweetsUseCase,
        downloadAllFactsUseCase: DownloadAllFactsUseCase,
        saveDownloadSweetDateUseCase: SaveDownloadSweetDateUseCase,
        saveDownloadFactDateUseCase: SaveDownloadFactDateUseCase
    ): WorkerFactory {
        return WorkersFactory(
            downloadAllFactsUseCase,
            downloadAllSweetsUseCase,
            saveDownloadFactDateUseCase,
            saveDownloadSweetDateUseCase)
    }

    @Provides
    fun provideBackgroundWorkManager(): BackgroundWorkManager {
        return BackgroundWorkManager()
    }
}
