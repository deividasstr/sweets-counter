package com.deividasstr.ui.base.di.modules

import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.repositories.SweetsRepo
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.AddNewSweetUseCase
import com.deividasstr.domain.usecases.GetAllConsumedSweetsCalsUseCase
import com.deividasstr.domain.usecases.GetAllSweetsUseCase
import com.deividasstr.domain.usecases.GetConsumedSweetsInPeriodUseCase
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.usecases.SearchSweetUseCase
import dagger.Module
import dagger.Provides

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
    fun provideGetConsumedSweetsInPeriodUseCase(repo: ConsumedSweetsRepo): GetConsumedSweetsInPeriodUseCase {
        return GetConsumedSweetsInPeriodUseCase(repo)
    }
}