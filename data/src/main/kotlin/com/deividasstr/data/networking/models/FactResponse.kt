package com.deividasstr.data.networking.models

import com.deividasstr.data.DbFact
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseFact(
    val id: Long,
    val fact: String,
    val timestamp: Long
)

fun DbFact(responseFact: ResponseFact): DbFact = DbFact.Impl(responseFact.id, responseFact.fact)

fun List<ResponseFact>.toFactModels(): List<DbFact> {
    return map { DbFact(it) }
}
