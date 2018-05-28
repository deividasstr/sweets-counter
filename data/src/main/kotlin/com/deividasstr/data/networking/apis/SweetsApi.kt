package com.deividasstr.data.networking.apis

import com.deividasstr.data.networking.models.ResponseSweet
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SweetsApi {

    @GET("sweets/")
    fun getAllSweets(): Single<List<ResponseSweet>>

    @GET("sweets/")
    fun getNewSweets(@Query("timestamp") afterTimestamp: Long): Single<List<ResponseSweet>>
}