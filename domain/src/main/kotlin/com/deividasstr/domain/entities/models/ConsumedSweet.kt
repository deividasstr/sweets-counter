package com.deividasstr.domain.entities.models

data class ConsumedSweet(
    val id: Long = 0,
    val sweetId: Long,
    val g: Long,
    val date: Long,
    val sweet: Sweet
)
