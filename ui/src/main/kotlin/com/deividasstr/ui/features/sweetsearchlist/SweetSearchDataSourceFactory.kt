package com.deividasstr.ui.features.sweetsearchlist

import androidx.paging.DataSource
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.ui.base.models.SweetUi

class SweetSearchDataSourceFactory(private val dataSource: SweetSearchDataSource) :
    DataSource.Factory<Int, SweetUi>() {

    private var query = ""

    override fun create(): DataSource<Int, SweetUi> {
        return dataSource.get(query).map { SweetUi(it) }
    }

    fun search(text: String) {
        query = text
    }
}
