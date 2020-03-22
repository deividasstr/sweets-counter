package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface PrefsRepo {
    suspend fun saveSweetsDownloadTime(): Either<Error, Either.None>
    suspend fun saveFactsDownloadTime(): Either<Error, Either.None>
}
