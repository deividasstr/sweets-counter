package com.deividasstr.data.networking.models

import com.deividasstr.data.store.models.SweetDb
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseSweet(
    val id: Long,
    val name: String,
    val kcal_100: Double,
    val serving_grams: Double,
    val fat_100: Double,
    val carbohydrate_100: Double,
    val protein_100: Double,
    val timestamp: Long
)

fun List<ResponseSweet>.toSweetModels(): List<SweetDb> {
    return this.map { SweetDb(it) }
}