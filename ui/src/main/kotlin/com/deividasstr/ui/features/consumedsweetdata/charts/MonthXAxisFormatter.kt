package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class MonthXAxisFormatter : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return monthDayFromDay(value.toInt())
    }

    private fun monthDayFromDay(day: Int): String {
        return "$day d"
    }
}