package com.deividasstr.data.networking.services

import com.deividasstr.data.networking.apis.SweetsApi
import com.deividasstr.data.networking.models.toSweetDbs
import com.deividasstr.data.store.models.SweetDb
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class SweetsService(private val sweetsApi: SweetsApi) {

    fun getAllSweets(): Single<List<SweetDb>> {
        return sweetsApi.getAllSweets().map { it.toSweetDbs() }
    }

    fun getNewSweets(afterTimestamp: Long): Single<List<SweetDb>> {
        return sweetsApi.getNewSweets(afterTimestamp).map { it.toSweetDbs() }
    }
}