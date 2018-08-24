package com.deividasstr.domain.enums

enum class MeasurementUnit {
    GRAM {
        override val ratioWithGrams: Int = 1
    },
    OUNCE {
        override val ratioWithGrams: Int = 28
    };

    abstract val ratioWithGrams: Int
}

fun MeasurementUnit.toggle(): MeasurementUnit {
    return when (this) {
        MeasurementUnit.GRAM -> MeasurementUnit.OUNCE
        MeasurementUnit.OUNCE -> MeasurementUnit.GRAM
    }
}