package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.ConsumedSweetDb_
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import io.objectbox.Box
import javax.inject.Singleton

@Singleton
class ConsumedSweetsDb(val db: Box<ConsumedSweetDb>) : ConsumedSweetsDao {

    override suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweetDb>> {
        val consumed = db.query().orderDesc(ConsumedSweetDb_.date).build().find()
        return Either.Right(consumed)
    }

    override suspend fun addSweet(sweet: ConsumedSweetDb): Either<Error, Either.None> {
        db.put(sweet)
        return Either.Right(Either.None())
    }

    override suspend fun getConsumedSweetsByPeriod(dateRange: DateRange): Either<Error, List<ConsumedSweetDb>> {
        val consumed = db.query().between(
            ConsumedSweetDb_.date,
            dateRange.start,
            dateRange.endInclusive
        ).build().find()
        return Either.Right(consumed)
    }
}