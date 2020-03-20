package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler

class BarValueFormatter : IValueFormatter {

    override fun getFormattedValue(
        value: Float,
        entry: Entry?,
        dataSetIndex: Int,
        viewPortHandler: ViewPortHandler?
    ): String {
        return when (value.toInt()) {
            0 -> ""
            else -> value.toInt().toString()
        }
    }
}