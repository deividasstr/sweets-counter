package com.deividasstr.ui.base.models

import com.deividasstr.domain.models.ConsumedSweet

data class ConsumedSweetUi(
    val id: Long,
    val sweetId: Int,
    val g: Long,
    val date: Long,
    val sweet: SweetUi
) {

    constructor(sweet: ConsumedSweet) : this(
        sweet.id,
        sweet.sweetId.toInt(),
        sweet.g,
        sweet.date,
        SweetUi(sweet.sweet))
}

fun List<ConsumedSweet>.toConsumedSweetUis(): List<ConsumedSweetUi> {
    return this.map { ConsumedSweetUi(it) }
}