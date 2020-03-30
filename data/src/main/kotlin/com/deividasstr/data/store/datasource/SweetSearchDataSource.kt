package com.deividasstr.data.store.datasource

import androidx.paging.DataSource
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.dbs.Sweet
import com.deividasstr.domain.entities.models.Sweet
import javax.inject.Singleton

@Singleton
class SweetSearchDataSource(private val sweetsDao: SweetsDao) {

    fun get(query: String): DataSource<Int, Sweet> {
        return sweetsDao.query(query).map { Sweet(it) }
    }
}
