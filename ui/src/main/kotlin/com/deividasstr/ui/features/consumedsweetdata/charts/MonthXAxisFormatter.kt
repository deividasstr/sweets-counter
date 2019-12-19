package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class MonthXAxisFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return value.toInt().toString()
    }
}