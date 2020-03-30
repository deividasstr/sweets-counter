package com.deividasstr.data.di.modules

import android.content.Context
import com.deividasstr.data.Database
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.data.store.dbs.ConsumedSweetsDb
import com.deividasstr.data.store.dbs.FactsDb
import com.deividasstr.data.store.dbs.SweetsDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        val driver = AndroidSqliteDriver(Database.Schema, context, "sweets_counter.db")
        return Database(driver)
    }

    @Singleton
    @Provides
    fun provideSweetsDb(db: Database): SweetsDao {
        return SweetsDb(db.sweetsDbQueries)
    }

    @Singleton
    @Provides
    fun provideFactsDb(db: Database): FactsDao {
        return FactsDb(db.factsDbQueries)
    }

    @Singleton
    @Provides
    fun provideConsumedSweetsDb(db: Database): ConsumedSweetsDao {
        return ConsumedSweetsDb(db.consumedSweetsDbQueries)
    }

    @Singleton
    @Provides
    fun provideSweetSearchDataSource(sweetsDao: SweetsDao): SweetSearchDataSource {
        return SweetSearchDataSource(sweetsDao)
    }
}
