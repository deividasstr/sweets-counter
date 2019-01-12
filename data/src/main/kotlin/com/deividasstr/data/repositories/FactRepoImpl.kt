package com.deividasstr.data.repositories

import com.deividasstr.data.networking.services.FactsService
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.toFact
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.FactRepo
import javax.inject.Singleton

@Singleton
class FactRepoImpl(private val factsDb: FactsDao, private val factsService: FactsService) :
    FactRepo {

    override suspend fun getRandomFact(currentFactId: Long): Either<Error, Fact> {
        return factsDb.getRandomFact(currentFactId).map { it.toFact() }
    }

    override suspend fun downloadAllFactsAndSave(): Either<Error, Either.None> {
        val facts = factsService.getAllFacts()
        return when (facts) {
            is Either.Right -> saveFacts(facts.b)
            is Either.Left -> facts
        }
    }

    private suspend fun saveFacts(facts: List<FactDb>): Either<Error, Either.None> {
        return factsDb.addFacts(facts)
    }
}