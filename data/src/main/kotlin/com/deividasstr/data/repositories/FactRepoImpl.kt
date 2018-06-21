package com.deividasstr.data.repositories

import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.toFact
import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.repositories.FactRepo
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class FactRepoImpl(private val factsDb: FactsDao, private val factsService: FactsService) :
    FactRepo {

    override fun getRandomFact(currentFactId: Long): Single<Fact> {
        return factsDb.getRandomFact(currentFactId).map { it.toFact() }
    }

    override fun downloadAllFactsAndSave(): Completable {
        return factsService.getAllFacts().flatMapCompletable { saveFacts(it) }
    }

    override fun downloadNewFactsAndSave(): Completable {
        return factsDb.getLastUpdateTimeStamp()
            .flatMap { factsService.getNewFacts(it) }
            .flatMapCompletable { saveFacts(it) }
    }

    private fun saveFacts(facts: List<FactDb>) : Completable {
        return factsDb.addFacts(facts)
    }
}