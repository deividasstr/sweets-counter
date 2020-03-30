package com.deividasstr.data.networking.services

import com.deividasstr.data.DbFact
import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.networking.models.toFactModels
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import javax.inject.Singleton

@Singleton
class FactsService(private val factsApi: FactsApi, private val networkManager: NetworkManager) :
    BaseNetworkService {

    suspend fun getAllFacts(): Either<Error, List<DbFact>> {
        return request(
            networkManager.networkAvailable,
            factsApi.getAllFacts(),
            { it.toFactModels() },
            emptyList())
    }

    suspend fun getNewFacts(afterTimestamp: Long): Either<Error, List<DbFact>> {
        return request(
            networkManager.networkAvailable,
            factsApi.getNewFacts(afterTimestamp),
            { it.toFactModels() },
            emptyList())
    }
}
