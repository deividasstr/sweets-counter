package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.Fact
import io.reactivex.Completable
import io.reactivex.Single

interface FactRepo {

    fun getRandomFact(currentFactId: Long): Single<Fact>

    fun downloadAllFactsAndSave(): Completable
}