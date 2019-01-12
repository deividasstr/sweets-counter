package com.deividasstr.data.networking.services

import com.deividasstr.data.R
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface BaseNetworkService {

    suspend fun <T, E> request(
        networkAvailable: Boolean,
        call: Deferred<Response<T>>,
        transform: (T) -> E,
        default: T): Either<Error, E> {

        if (!networkAvailable)
            return Either.Left(Error(R.string.error_network_unavailable))

        return try {
            val response = call.await()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Error(R.string.error_server))
            }
        } catch (exception: Throwable) {
            Either.Left(Error(R.string.error_server))
        }
    }
}