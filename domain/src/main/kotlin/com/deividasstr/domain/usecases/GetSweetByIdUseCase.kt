package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.framework.ParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo

class GetSweetByIdUseCase(private val repo: SweetsRepo) : ParamsUseCase<Sweet, Long> {

    override suspend fun run(params: Long): Either<Error, Sweet> {
        return repo.getSweetById(params)
    }
}
