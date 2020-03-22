package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface SweetsDao {

    suspend fun getSweetById(id: Long): Either<Error, SweetDb>

    suspend fun getAllSweets(): Either<Error, List<SweetDb>>

    suspend fun addSweets(sweets: List<SweetDb>): Either<Error, Either.None>

    suspend fun addSweet(sweet: SweetDb): Either<Error, Either.None>
}
