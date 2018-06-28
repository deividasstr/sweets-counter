package com.deividasstr.utils.di

import android.content.Context
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.dbs.ConsumedSweetsDb
import com.deividasstr.data.store.dbs.FactsDb
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.MyObjectBox
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.utils.StrictModePermitter
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class TestDbModule(private val sweetsDb: SweetsDb) {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): BoxStore {
        return StrictModePermitter.permitDiskReads {
            MyObjectBox.builder().androidContext(context).build()
        }
    }

    @Singleton
    @Provides
    fun provideSweetsDao(store: BoxStore): SweetsDao {
        return SweetsDb(store.boxFor(SweetDb::class.java))
    }

    @Singleton
    @Provides
    fun provideSweetsDb(): SweetsDb {
        return sweetsDb
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
}