package com.deividasstr.data.store.datasource

import androidx.paging.DataSource
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.domain.models.Sweet
import io.objectbox.android.ObjectBoxDataSource
import io.objectbox.query.Query

class SweetSearchDataSource(private val dbSweets: Query<SweetDb>) {

    fun get(): DataSource<Int, Sweet> {
        return ObjectBoxDataSource.Factory<SweetDb>(dbSweets).map { ::converter.invoke(it) }
            .create()
    }

    private fun converter(sweetDb: SweetDb): Sweet {
        return sweetDb.toSweet()
    }
}