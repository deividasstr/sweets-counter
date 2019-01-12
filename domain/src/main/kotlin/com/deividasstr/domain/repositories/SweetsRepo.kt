package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.monads.Either

interface SweetsRepo {

    suspend fun getSweetById(id: Long): Either<Error, Sweet>

    suspend fun newSweet(sweet: Sweet): Either<Error, Either.None>

    suspend fun downloadAndSaveAllSweets(): Either<Error, Either.None>

    suspend fun downloadAndSaveNewSweets(): Either<Error, Either.None>
}