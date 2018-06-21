package com.deividasstr.ui.base.models

import com.deividasstr.domain.entities.Sweet

data class SweetUi(val id: Long,
    val name: String,
    val calsPer100: Double,
    val servingG: Double,
    val fatG: Double,
    val carbsG: Double,
    val proteinG: Double) {

    constructor(sweet: Sweet) : this(
        sweet.id, sweet.name, sweet.calsPer100, sweet.servingG,
        sweet.fatG, sweet.carbsG, sweet.proteinG
    )
}
