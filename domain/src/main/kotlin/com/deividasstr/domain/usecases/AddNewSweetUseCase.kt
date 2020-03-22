package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.framework.ParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo

class AddNewSweetUseCase(private val repo: SweetsRepo) : ParamsUseCase<Either.None, Sweet> {

    override suspend fun run(params: Sweet): Either<Error, Either.None> {
        return repo.newSweet(params)
    }
}
