package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.ConsumedSweetModel
import com.deividasstr.domain.utils.DateRange
import io.reactivex.Completable
import io.reactivex.Single

interface ConsumedSweetsDao {

    fun getAllConsumedSweets(): Single<List<ConsumedSweetModel>>

    fun addSweet(sweet: ConsumedSweetModel): Completable

    fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweetModel>>
}