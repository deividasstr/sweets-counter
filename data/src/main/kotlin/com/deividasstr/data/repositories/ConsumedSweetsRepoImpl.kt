package com.deividasstr.data.repositories

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.toConsumedSweets
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import javax.inject.Singleton

@Singleton
class ConsumedSweetsRepoImpl(
    private val consumedSweetsDb: ConsumedSweetsDao
) : ConsumedSweetsRepo {

    override suspend fun addSweet(sweet: ConsumedSweet): Either<Error, Either.None> {
        return consumedSweetsDb.addSweet(ConsumedSweetDb(sweet))
    }

    override suspend fun getAllConsumedSweets(): Either<Error, List<ConsumedSweet>> {
        return consumedSweetsDb.getAllConsumedSweets().map { it.toConsumedSweets() }
    }
}