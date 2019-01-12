package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.monads.Either

interface FactRepo {

    suspend fun getRandomFact(currentFactId: Long): Either<Error, Fact>

    suspend fun downloadAllFactsAndSave(): Either<Error, Either.None>
}