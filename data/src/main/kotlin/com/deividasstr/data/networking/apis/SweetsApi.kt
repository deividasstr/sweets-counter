package com.deividasstr.data.networking.apis

import com.deividasstr.data.networking.models.ResponseSweet
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SweetsApi {

    @GET("sweets/")
    fun getAllSweets(): Deferred<Response<List<ResponseSweet>>>

    @GET("sweets/")
    fun getNewSweets(@Query("timestamp") afterTimestamp: Long): Deferred<Response<List<ResponseSweet>>>
}