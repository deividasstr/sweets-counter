package com.deividasstr.data.store.daos

import com.deividasstr.data.DbFact
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface FactsDao {

    suspend fun addFacts(facts: List<DbFact>): Either<Error, Either.None>

    suspend fun getFact(currentFactId: Long): Either<Error, DbFact>
}
