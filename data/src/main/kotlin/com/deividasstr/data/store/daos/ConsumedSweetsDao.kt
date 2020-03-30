package com.deividasstr.data.store.daos

import com.deividasstr.data.DbConsumedSweet
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface ConsumedSweetsDao {

    suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweet>>

    suspend fun addSweet(sweet: DbConsumedSweet): Either<Error, Either.None>

    suspend fun getConsumedSweetsByPeriod(dateRange: DateRange): Either<Error, List<ConsumedSweet>>
}
