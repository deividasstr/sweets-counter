package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.FactDb
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface FactsDao {

    suspend fun addFacts(facts: List<FactDb>): Either<Error, Either.None>

    suspend fun getRandomFact(currentFactId: Long): Either<Error, FactDb>
}