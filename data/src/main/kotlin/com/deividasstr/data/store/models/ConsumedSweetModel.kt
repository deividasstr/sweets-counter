package com.deividasstr.data.store.models

import com.deividasstr.domain.entities.ConsumedSweet
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ConsumedSweetModel(
    @Id(assignable = true) var id: Long,
    val sweetId: Int,
    val g: Int,
    val date: Long
) {
    constructor(sweet: ConsumedSweet) : this(sweet.id, sweet.sweetId, sweet.g, sweet.date)
}

fun List<ConsumedSweetModel>.toConsumedSweets(): List<ConsumedSweet> {
    return this.map { it.toConsumedSweet() }
}

fun ConsumedSweetModel.toConsumedSweet(): ConsumedSweet {
    return ConsumedSweet(id, sweetId, g, date)
}

fun List<ConsumedSweet>.toConsumedSweetModels(): List<ConsumedSweetModel> {
    return this.map { ConsumedSweetModel(it) }
}