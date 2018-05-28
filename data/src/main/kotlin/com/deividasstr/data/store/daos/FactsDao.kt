package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.FactModel
import io.reactivex.Completable
import io.reactivex.Single

interface FactsDao {

    fun addFacts(facts: List<FactModel>): Completable

    fun getRandomFact(currentFactId: Long): Single<FactModel>

    fun getLastUpdateTimeStamp(): Single<Long>
}