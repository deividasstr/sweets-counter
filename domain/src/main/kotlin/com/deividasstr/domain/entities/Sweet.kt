package com.deividasstr.domain.entities

data class Sweet(
    val id: Long,
    val name: String,
    val calsPer100: Double,
    val servingG: Double,
    val fatG: Double,
    val carbsG: Double,
    val proteinG: Double
)