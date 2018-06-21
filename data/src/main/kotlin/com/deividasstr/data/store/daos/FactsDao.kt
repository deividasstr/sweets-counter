package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.FactDb
import io.reactivex.Completable
import io.reactivex.Single

interface FactsDao {

    fun addFacts(facts: List<FactDb>): Completable

    fun getRandomFact(currentFactId: Long): Single<FactDb>

    fun getLastUpdateTimeStamp(): Single<Long>
}