package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.framework.ParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.FactRepo

class GetRandomFactUseCase(private val repo: FactRepo) : ParamsUseCase<Fact, Long> {

    override suspend fun run(params: Long): Either<Error, Fact> {
        return repo.getRandomFact(params)
    }
}