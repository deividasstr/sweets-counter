package com.deividasstr.data.store.models

import com.deividasstr.domain.models.ConsumedSweet
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class ConsumedSweetDb(
    @Id var id: Long,
    val sweetId: Long,
    val g: Long,
    val date: Long
) {
    constructor(sweet: ConsumedSweet) : this(sweet.id, sweet.sweetId, sweet.g, sweet.date)

    lateinit var sweet: ToOne<SweetDb>

    val sweetDb: SweetDb
        get() = sweet.target
}

fun List<ConsumedSweetDb>.toConsumedSweets(): List<ConsumedSweet> {
    return map { it.toConsumedSweet() }
}

fun ConsumedSweetDb.toConsumedSweet(): ConsumedSweet {
    return ConsumedSweet(id, sweetId, g, date, sweetDb.toSweet())
}