package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.Locale
import org.threeten.bp.Month
import org.threeten.bp.format.TextStyle

class YearXAxisFormatter : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return monthNameFromNumber(value.toInt())
    }

    private fun monthNameFromNumber(month: Int): String {
        return Month.of(month).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
    }
}
