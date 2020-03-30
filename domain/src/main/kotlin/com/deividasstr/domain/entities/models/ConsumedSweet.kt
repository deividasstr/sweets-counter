package com.deividasstr.domain.entities.models

data class ConsumedSweet(
    val grams: Long,
    val date: Long,
    val sweet: Sweet
) {

    constructor(
        grams: Long,
        date: Long,
        sweetId: Long,
        name: String,
        calsPer100: Long,
        fatG: Double,
        carbsG: Double,
        sugarG: Double,
        proteinG: Double
    ) : this(grams, date, Sweet(sweetId, name, calsPer100, fatG, carbsG, sugarG, proteinG))
}

