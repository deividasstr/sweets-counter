package com.deividasstr.data.networking.services

import com.deividasstr.data.networking.apis.FactsApi
import com.deividasstr.data.networking.models.toFactModels
import com.deividasstr.data.store.models.FactModel
import io.reactivex.Single

class FactsService(private val factsApi: FactsApi) {

    fun getAllFacts(): Single<List<FactModel>> {
        return factsApi.getAllFacts().map { it.toFactModels() }
    }

    fun getNewFacts(afterTimestamp: Long): Single<List<FactModel>> {
        return factsApi.getNewFacts(afterTimestamp).map { it.toFactModels() }
    }
}