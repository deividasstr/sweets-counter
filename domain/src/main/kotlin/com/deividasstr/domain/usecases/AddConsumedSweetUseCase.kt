package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.framework.ParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo

class AddConsumedSweetUseCase(private val repo: ConsumedSweetsRepo) :
    ParamsUseCase<Either.None, ConsumedSweet> {

    override suspend fun run(params: ConsumedSweet): Either<Error, Either.None> {
        return repo.addSweet(params)
    }
}
