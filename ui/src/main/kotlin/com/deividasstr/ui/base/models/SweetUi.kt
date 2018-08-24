package com.deividasstr.ui.base.models

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.ui.features.sweetdetails.SweetRating

data class SweetUi(
    val id: Long,
    val name: String,
    val calsPer100: Long,
    val fatG: Double,
    val carbsG: Double,
    val sugarG: Double,
    val proteinG: Double
) {

    constructor(sweet: Sweet) : this(
        sweet.id, sweet.name, sweet.calsPer100,
        sweet.fatG, sweet.carbsG, sweet.sugarG, sweet.proteinG
    )

    fun sweetRating(): SweetRating {
        return when {
            calsPer100 >= 400 || sugarG >= 30 -> SweetRating.BAD
            calsPer100 >= 200 || sugarG >= 10 -> SweetRating.AVERAGE
            else -> SweetRating.GOOD
        }
    }
}

fun List<Sweet>.toSweetUis(): List<SweetUi> {
    return this.map { SweetUi(it) }
}
