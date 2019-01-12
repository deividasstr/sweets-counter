package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.framework.NoParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.FactRepo

class DownloadAllFactsUseCase(private val repo: FactRepo) : NoParamsUseCase<Either.None> {

    override suspend fun run(): Either<Error, Either.None> {
        return repo.downloadAllFactsAndSave()
    }
}