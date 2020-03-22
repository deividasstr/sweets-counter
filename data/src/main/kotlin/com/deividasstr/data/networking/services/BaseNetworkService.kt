package com.deividasstr.data.networking.services

import com.deividasstr.data.R
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import retrofit2.Response

interface BaseNetworkService {

    suspend fun <T, E> request(
        networkAvailable: Boolean,
        call: Response<T>,
        transform: (T) -> E,
        default: T
    ): Either<Error, E> {

        if (!networkAvailable)
            return Either.Left(Error(R.string.error_network_unavailable))

        return try {
            when (call.isSuccessful) {
                true -> Either.Right(transform((call.body() ?: default)))
                false -> Either.Left(Error(R.string.error_server))
            }
        } catch (exception: Throwable) {
            Either.Left(Error(R.string.error_server))
        }
    }
}
