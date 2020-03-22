package com.deividasstr.data.di.modules

import android.content.Context
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.data.store.dbs.ConsumedSweetsDb
import com.deividasstr.data.store.dbs.FactsDb
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.MyObjectBox
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.utils.StrictModePermitter.permitDiskReads
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): BoxStore {
        return permitDiskReads { MyObjectBox.builder().androidContext(context).build() }
    }

    @Singleton
    @Provides
    fun provideSweetsDao(store: BoxStore): SweetsDao {
        return SweetsDb(store.boxFor(SweetDb::class.java))
    }

    @Singleton
    @Provides
    fun provideSweetsDb(store: BoxStore): SweetsDb {
        return SweetsDb(store.boxFor(SweetDb::class.java))
    }

    @Singleton
    @Provides
    fun provideFactsDb(store: BoxStore): FactsDao {
        return FactsDb(store.boxFor(FactDb::class.java))
    }

    @Singleton
    @Provides
    fun provideConsumedSweetsDb(store: BoxStore): ConsumedSweetsDao {
        return ConsumedSweetsDb(store.boxFor(ConsumedSweetDb::class.java))
    }

    @Singleton
    @Provides
    fun provideSweetSearchDataSource(sweetsDb: SweetsDb): SweetSearchDataSource {
        return SweetSearchDataSource(sweetsDb)
    }
}
