package com.deividasstr.data.networking.services

import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.models.toFactModels
import com.deividasstr.data.store.models.FactDb
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class FactsService(private val factsApi: FactsApi) {

    fun getAllFacts(): Single<List<FactDb>> {
        return factsApi.getAllFacts().map { it.toFactModels() }
    }

    fun getNewFacts(afterTimestamp: Long): Single<List<FactDb>> {
        return factsApi.getNewFacts(afterTimestamp).map { it.toFactModels() }
    }
}