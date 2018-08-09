package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlin.math.roundToLong

class YAxisFormatter : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return "${value.roundToLong()} kcal"
    }
}