package com.deividasstr.ui.base.di.modules

import android.content.Context
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.dbs.ConsumedSweetsDb
import com.deividasstr.data.store.dbs.FactsDb
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.store.models.ConsumedSweetModel
import com.deividasstr.data.store.models.FactModel
import com.deividasstr.data.store.models.MyObjectBox
import com.deividasstr.data.store.models.SweetModel
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class DbModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): BoxStore {
        return MyObjectBox.builder().androidContext(context).build()
    }

    @Singleton
    @Provides
    fun provideSweetsDb(store: BoxStore): SweetsDao {
        return SweetsDb(store.boxFor(SweetModel::class.java))
    }

    @Singleton
    @Provides
    fun provideFactsDb(store: BoxStore): FactsDao {
        return FactsDb(store.boxFor(FactModel::class.java))
    }

    @Singleton
    @Provides
    fun provideConsumedSweetsDb(store: BoxStore): ConsumedSweetsDao {
        return ConsumedSweetsDb(store.boxFor(ConsumedSweetModel::class.java))
    }
}