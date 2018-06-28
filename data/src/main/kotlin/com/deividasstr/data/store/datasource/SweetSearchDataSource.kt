package com.deividasstr.data.store.datasource

import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.domain.entities.Sweet
import io.objectbox.query.LazyList

class SweetSearchDataSource(dbSweets: LazyList<SweetDb>) :
    ObjectBoxDataSource<Sweet>(dbSweets, ::converter) {

    companion object {
        fun converter(any: Any): Sweet {
            return (any as SweetDb).toSweet()
        }
    }
}