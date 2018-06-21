package com.deividasstr.data.store.models

import com.deividasstr.domain.entities.ConsumedSweet
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class ConsumedSweetDb(
    @Id(assignable = true) var id: Long,
    val sweetId: Int,
    val g: Int,
    val date: Long
) {
    constructor(sweet: ConsumedSweet) : this(sweet.id, sweet.sweetId, sweet.g, sweet.date)
}

fun List<ConsumedSweetDb>.toConsumedSweets(): List<ConsumedSweet> {
    return this.map { it.toConsumedSweet() }
}

fun ConsumedSweetDb.toConsumedSweet(): ConsumedSweet {
    return ConsumedSweet(id, sweetId, g, date)
}

fun List<ConsumedSweet>.toConsumedSweetModels(): List<ConsumedSweetDb> {
    return this.map { ConsumedSweetDb(it) }
}