package com.deividasstr.data.networking.services

import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.models.toSweetModels
import com.deividasstr.data.store.models.SweetModel
import io.reactivex.Single

class SweetsService(private val sweetsApi: SweetsApi) {

    fun getAllSweets(): Single<List<SweetModel>> {
        return sweetsApi.getAllSweets().map { it.toSweetModels() }
    }

    fun getNewSweets(afterTimestamp: Long): Single<List<SweetModel>> {
        return sweetsApi.getNewSweets(afterTimestamp).map { it.toSweetModels() }
    }
}