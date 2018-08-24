package com.deividasstr.ui.base.models

import com.deividasstr.domain.entities.ConsumedSweet

data class ConsumedSweetUi(val id: Long, val sweetId: Int, val g: Long, val date: Long) {

    constructor(sweet: ConsumedSweet) : this(sweet.id, sweet.sweetId.toInt(), sweet.g, sweet.date)
}

fun List<ConsumedSweet>.toConsumedSweetUis(): List<ConsumedSweetUi> {
    return this.map { ConsumedSweetUi(it) }
}