package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface ConsumedSweetsDao {

    suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweetDb>>

    suspend fun addSweet(sweet: ConsumedSweetDb): Either<Error, Either.None>

    suspend fun getConsumedSweetsByPeriod(dateRange: DateRange): Either<Error, List<ConsumedSweetDb>>
}
