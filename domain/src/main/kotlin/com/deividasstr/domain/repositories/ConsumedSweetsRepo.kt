package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.utils.DateRange
import io.reactivex.Completable
import io.reactivex.Single

interface ConsumedSweetsRepo {

    fun getTotalCalsConsumed(): Single<Int>

    fun addSweet(sweet: ConsumedSweet): Completable

    fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweet>>

    fun getAllConsumedSweets(): Single<List<ConsumedSweet>>
}