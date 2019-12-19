package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.math.roundToLong

class YAxisFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return "${value.roundToLong()}\nkcal"
    }
}