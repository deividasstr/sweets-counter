package com.deividasstr.data.store.models

import com.deividasstr.data.networking.models.ResponseSweet
import com.deividasstr.domain.entities.Sweet
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SweetDb(
    @Id(assignable = true) var id: Long,
    val name: String,
    val calsPer100: Double,
    val servingG: Double,
    val fatG: Double,
    val carbsG: Double,
    val proteinG: Double,
    val addedTimestamp: Long
) {
    constructor(sweet: Sweet) : this(
        sweet.id, sweet.name, sweet.calsPer100, sweet.servingG,
        sweet.fatG, sweet.carbsG, sweet.proteinG, 0
    )
    constructor(sweet: ResponseSweet) : this(
        sweet.id, sweet.name, sweet.kcal_100, sweet.serving_grams,
        sweet.fat_100, sweet.carbohydrate_100, sweet.protein_100, sweet.timestamp
    )
}

fun List<SweetDb>.toSweets(): List<Sweet> {
    return this.map { it.toSweet() }
}

fun SweetDb.toSweet(): Sweet {
    return Sweet(id, name, calsPer100, servingG, fatG, carbsG, proteinG)
}
