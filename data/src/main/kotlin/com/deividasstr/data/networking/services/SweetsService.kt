package com.deividasstr.data.networking.services

import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.networking.models.toSweetDbs
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import javax.inject.Singleton

@Singleton
class SweetsService(private val sweetsApi: SweetsApi, private val networkManager: NetworkManager) :
    BaseNetworkService {

    suspend fun getAllSweets(): Either<Error, List<SweetDb>> {
        return request(
            networkManager.networkAvailable,
            sweetsApi.getAllSweets(),
            { it.toSweetDbs() },
            emptyList())
    }

    suspend fun getNewSweets(afterTimestamp: Long): Either<Error, List<SweetDb>> {
        return request(
            networkManager.networkAvailable,
            sweetsApi.getNewSweets(afterTimestamp),
            { it.toSweetDbs() },
            emptyList())
    }
}
