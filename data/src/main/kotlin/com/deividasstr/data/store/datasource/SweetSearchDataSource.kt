package com.deividasstr.data.store.datasource

import androidx.paging.DataSource
import com.deividasstr.data.store.dbs.SweetsDb
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.domain.entities.models.Sweet
import io.objectbox.android.ObjectBoxDataSource
import javax.inject.Singleton

@Singleton
class SweetSearchDataSource(private val db: SweetsDb) {

    fun get(query: String): DataSource<Int, Sweet> {
        val sweetQuery = db.query(query)
        return ObjectBoxDataSource.Factory<SweetDb>(sweetQuery)
            .map { ::converter.invoke(it) }
            .create()
    }

    private fun converter(sweetDb: SweetDb): Sweet {
        return sweetDb.toSweet()
    }
}
