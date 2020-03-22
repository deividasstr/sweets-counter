package com.deividasstr.ui.base.di.modules

import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.AddNewSweetUseCase
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator
import com.deividasstr.ui.features.sweetsearchlist.SweetSearchDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    fun provideAddConsumedSweetUseCase(repo: ConsumedSweetsRepo): AddConsumedSweetUseCase {
        return AddConsumedSweetUseCase(repo)
    }

    @Provides
    fun provideAddNewSweetUseCase(repo: SweetsRepo): AddNewSweetUseCase {
        return AddNewSweetUseCase(repo)
    }

    @Provides
    fun provideGetRandomFactUseCase(repo: FactRepo): GetRandomFactUseCase {
        return GetRandomFactUseCase(repo)
    }

    @Provides
    fun provideGetSweetByIdUseCase(repo: SweetsRepo): GetSweetByIdUseCase {
        return GetSweetByIdUseCase(repo)
    }

    @Provides
    fun provideDownloadAllSweetsUseCase(repo: SweetsRepo): DownloadAllSweetsUseCase {
        return DownloadAllSweetsUseCase(repo)
    }

    @Provides
    fun provideSweetSearchDataSourceFactory(dataSource: SweetSearchDataSource):
        SweetSearchDataSourceFactory {
        return SweetSearchDataSourceFactory(dataSource)
    }

    @Provides
    fun provideSaveDownloadSweetsDateUseCase(repo: PrefsRepo): SaveDownloadSweetDateUseCase {
        return SaveDownloadSweetDateUseCase(repo)
    }

    @Provides
    fun provideSaveDownloadFactsDateUseCase(repo: PrefsRepo): SaveDownloadFactDateUseCase {
        return SaveDownloadFactDateUseCase(repo)
    }

    @Provides
    fun provideDownloadAllFactsUseCase(repo: FactRepo): DownloadAllFactsUseCase {
        return DownloadAllFactsUseCase(repo)
    }

    @Provides
    fun provideGetAllConsumedSweetsUseCase(repo: ConsumedSweetsRepo): GetAllConsumedSweetsUseCase {
        return GetAllConsumedSweetsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDateTimeHandler(): DateTimeHandler {
        return DateTimeHandler()
    }

    @Provides
    @Singleton
    fun provideConsumedDataGenerator(): ConsumedDataGenerator {
        return ConsumedDataGenerator
    }
}