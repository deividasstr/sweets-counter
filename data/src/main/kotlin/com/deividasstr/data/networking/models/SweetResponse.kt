package com.deividasstr.data.networking.models

import com.deividasstr.data.DbSweet
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseSweet(
    val id: Long,
    val name: String,
    val kcal_100: Long,
    val fat_100: Double,
    val carbohydrate_100: Double,
    var sugar_100: Double,
    val protein_100: Double
)

fun List<ResponseSweet>.toSweetDbs(): List<DbSweet> {
    return map { (id, name, kcal_100, fat_100, carbohydrate_100, sugar_100, protein_100) ->
        DbSweet.Impl(id, name, kcal_100, fat_100, carbohydrate_100, sugar_100, protein_100)
    }
}
