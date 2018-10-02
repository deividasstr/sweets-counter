package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.domain.entities.DateRange
import io.reactivex.Completable
import io.reactivex.Single

interface ConsumedSweetsDao {

    fun getAllConsumedSweets(): Single<List<ConsumedSweetDb>>

    fun addSweet(sweet: ConsumedSweetDb): Completable

    fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweetDb>>
}