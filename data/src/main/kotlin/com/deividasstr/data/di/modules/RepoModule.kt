package com.deividasstr.data.di.modules

import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.repositories.ConsumedSweetsRepoImpl
import com.deividasstr.data.repositories.FactRepoImpl
import com.deividasstr.data.repositories.SweetsRepoImpl
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.repositories.SweetsRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideSweetsRepo(sweetsDao: SweetsDao, sweetsService: SweetsService): SweetsRepo {
        return SweetsRepoImpl(sweetsDao, sweetsService)
    }

    @Singleton
    @Provides
    fun provideFactsRepo(factsDao: FactsDao, factsService: FactsService): FactRepo {
        return FactRepoImpl(factsDao, factsService)
    }

    @Singleton
    @Provides
    fun provideConsumedSweetsRepo(
        consumedSweetsDao: ConsumedSweetsDao,
        sweetsDao: SweetsDao
    ): ConsumedSweetsRepo {
        return ConsumedSweetsRepoImpl(consumedSweetsDao, sweetsDao)
    }
}