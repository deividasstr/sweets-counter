package com.deividasstr.ui.base.di.modules

import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.AddNewSweetUseCase
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase
import com.deividasstr.domain.usecases.GetAllConsumedSweetsCalsUseCase
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetAllSweetsUseCase
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase
import com.deividasstr.domain.usecases.SearchSweetUseCase
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.features.sweetsearchlist.SweetSearchDataSourceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@DebugOpenClass
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
    fun provideGetAllSweetsUseCase(repo: SweetsRepo): GetAllSweetsUseCase {
        return GetAllSweetsUseCase(repo)
    }

    @Provides
    fun provideGetAllConsumedSweetsCalsUseCase(repo: ConsumedSweetsRepo): GetAllConsumedSweetsCalsUseCase {
        return GetAllConsumedSweetsCalsUseCase(repo)
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
    fun provideSearchSweetUseCase(repo: SweetsRepo): SearchSweetUseCase {
        return SearchSweetUseCase(repo)
    }

    @Provides
    fun provideDownloadAllSweetsUseCase(repo: SweetsRepo): DownloadAllSweetsUseCase {
        return DownloadAllSweetsUseCase(repo)
    }

    @Provides
    fun provideSweetSearchDataSourceFactory(db: SweetsDb): SweetSearchDataSourceFactory {
        return SweetSearchDataSourceFactory(db)
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
    fun provideGetSweetsByIdsUseCase(repo: SweetsRepo): GetSweetsByIdsUseCase {
        return GetSweetsByIdsUseCase(repo)
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
}