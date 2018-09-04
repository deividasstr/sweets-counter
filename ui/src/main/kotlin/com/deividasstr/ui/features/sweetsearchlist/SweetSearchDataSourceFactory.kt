package com.deividasstr.ui.features.sweetsearchlist

import androidx.paging.DataSource
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.ui.base.models.SweetUi
import javax.inject.Singleton

@Singleton
class SweetSearchDataSourceFactory(private val db: SweetsDb) :
    DataSource.Factory<Int, SweetUi>() {

    private var query = ""

    override fun create(): DataSource<Int, SweetUi> {
        return SweetSearchDataSource(db.query(query)).get().map { SweetUi(it) }
    }

    fun search(text: String) {
        query = text
    }
}