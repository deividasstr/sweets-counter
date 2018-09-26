package com.deividasstr.ui.features.consumedsweetdata.models

import com.deividasstr.ui.R
import com.deividasstr.ui.features.sweetdetails.SweetRating

data class LowerPeriodModel(
    val cals: Long = 0,
    val weight: Long = 0,
    val unitsConsumed: Long = 0,
    val sweetsPopularityData: List<PopularitySweetUi>?,
    val sweetsRatingData: Map<SweetRating, Long>?,
    val subDateRangeText: String?,
    val unitStringRes: Int
) {

    constructor() : this(
        sweetsPopularityData = null,
        sweetsRatingData = null,
        subDateRangeText = null,
        unitStringRes = R.string.unit_grams)
}