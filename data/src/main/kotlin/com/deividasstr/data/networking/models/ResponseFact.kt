package com.deividasstr.data.networking.models

import com.deividasstr.data.store.models.FactModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseFact(
    val id: Long,
    val fact: String,
    val timestamp: Long
)

fun List<ResponseFact>.toFactModels(): List<FactModel> {
    return this.map { FactModel(it) }
}