package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface ConsumedSweetsRepo {

    suspend fun addSweet(sweet: ConsumedSweet): Either<Error, Either.None>

    suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweet>>
}