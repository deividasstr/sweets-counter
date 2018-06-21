package com.deividasstr.ui.features.sweetsearchlist

import android.arch.paging.DataSource
import com.deividasstr.data.store.datasource.SweetSearchDataSource
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.SweetDb_
import com.deividasstr.ui.base.models.SweetUi
import io.objectbox.Box

class SweetSearchDataSourceFactory(private val box: Box<SweetDb>) :
    DataSource.Factory<Int, SweetUi>() {

    var query = ""

    override fun create(): DataSource<Int, SweetUi> {
        val lazyList = box.query().contains(SweetDb_.name, query).build().findLazyCached()
        return SweetSearchDataSource(lazyList).map { SweetUi(it) }
    }

    fun search(text: String) {
        query = text
    }
}