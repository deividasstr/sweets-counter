package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.framework.NoParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo

class DownloadAllSweetsUseCase(private val repo: SweetsRepo) : NoParamsUseCase<Either.None> {

    override suspend fun run(): Either<Error, Either.None> {
        return repo.downloadAndSaveAllSweets()
    }
}
