package com.deividasstr.data.store.dbs

import com.deividasstr.data.ConsumedSweetsDbQueries
import com.deividasstr.data.DbConsumedSweet
import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import javax.inject.Singleton

@Singleton
class ConsumedSweetsDb(private val queries: ConsumedSweetsDbQueries) : ConsumedSweetsDao {

    override suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweet>> {
        val consumed = queries.getAll(::ConsumedSweet).executeAsList()
        return Either.Right(consumed)
    }

    override suspend fun addSweet(sweet: DbConsumedSweet): Either<Error, Either.None> {
        queries.insert(sweet.sweetId, sweet.grams, sweet.date)
        return Either.Right(Either.None())
    }

    override suspend fun getConsumedSweetsByPeriod(dateRange: DateRange): Either<Error, List<ConsumedSweet>> {
        val consumed = queries.getBetweenDates(
            dateRange.start,
            dateRange.endInclusive,
            ::ConsumedSweet
        ).executeAsList()
        return Either.Right(consumed)
    }
}

fun DbConsumedSweet(sweet: ConsumedSweet): DbConsumedSweet = DbConsumedSweet.Impl(sweet.sweet.id, sweet.grams, sweet.date)