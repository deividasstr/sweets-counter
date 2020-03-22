package com.deividasstr.data.networking.models

import com.deividasstr.data.store.models.FactDb
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseFact(
    val id: Long,
    val fact: String,
    val timestamp: Long
)

fun List<ResponseFact>.toFactModels(): List<FactDb> {
    return this.map { FactDb(it) }
}
