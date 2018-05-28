package com.deividasstr.data.networking.apis

import com.deividasstr.data.networking.models.ResponseFact
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsApi {

    @GET("facts/")
    fun getAllFacts(): Single<List<ResponseFact>>

    @GET("facts/")
    fun getNewFacts(@Query("timestamp") afterTimestamp: Long): Single<List<ResponseFact>>
}