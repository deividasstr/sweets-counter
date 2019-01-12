package com.deividasstr.data.networking.apis

import com.deividasstr.data.networking.models.ResponseFact
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsApi {

    @GET("facts/")
    fun getAllFacts(): Deferred<Response<List<ResponseFact>>>

    @GET("facts/")
    fun getNewFacts(@Query("timestamp") afterTimestamp: Long): Deferred<Response<List<ResponseFact>>>
}