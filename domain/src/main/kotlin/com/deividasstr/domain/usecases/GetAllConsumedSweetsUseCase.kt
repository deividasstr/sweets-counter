package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.framework.NoParamsUseCase
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.ConsumedSweetsRepo

class GetAllConsumedSweetsUseCase(private val repo: ConsumedSweetsRepo) :
    NoParamsUseCase<List<ConsumedSweet>> {

    override suspend fun run(): Either<Error, List<ConsumedSweet>> {
        return repo.getAllConsumedSweets()
    }
}