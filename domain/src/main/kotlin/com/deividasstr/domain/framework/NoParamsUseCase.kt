package com.deividasstr.domain.framework

import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface NoParamsUseCase<out Type : Any> {

    suspend fun run(): Either<Error, Type>

    suspend operator fun invoke(onResult: (Either<Error, Type>) -> Unit) {
        onResult(run())
    }
}
