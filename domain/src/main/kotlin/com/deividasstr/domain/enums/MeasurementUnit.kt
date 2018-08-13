package com.deividasstr.domain.enums

enum class MeasurementUnit {
    GRAM {
        override val ratioWithGrams: Double = 1.0
    },
    OUNCE {
        override val ratioWithGrams: Double = 28.35
    };

    abstract val ratioWithGrams: Double
}

fun MeasurementUnit.toggle(): MeasurementUnit {
    return when (this) {
        MeasurementUnit.GRAM -> MeasurementUnit.OUNCE
        MeasurementUnit.OUNCE -> MeasurementUnit.GRAM
    }
}