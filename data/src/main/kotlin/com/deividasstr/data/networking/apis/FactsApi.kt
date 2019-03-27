package com.deividasstr.data.networking.apis

import com.deividasstr.data.networking.models.ResponseFact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FactsApi {

    @GET("facts/")
    suspend fun getAllFacts(): Response<List<ResponseFact>>

    @GET("facts/")
    suspend fun getNewFacts(@Query("timestamp") afterTimestamp: Long): Response<List<ResponseFact>>
}