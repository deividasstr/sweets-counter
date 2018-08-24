package com.deividasstr.domain.entities

data class Sweet(
    val id: Long,
    val name: String,
    val calsPer100: Long,
    val fatG: Double,
    val carbsG: Double,
    val sugarG: Double,
    val proteinG: Double
)