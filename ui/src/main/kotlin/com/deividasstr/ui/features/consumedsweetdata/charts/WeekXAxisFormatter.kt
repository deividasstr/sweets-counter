package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import org.threeten.bp.DayOfWeek
import org.threeten.bp.format.TextStyle
import java.util.Locale

class WeekXAxisFormatter : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        return weekDayFromDay(value.toInt())
    }

    private fun weekDayFromDay(day: Int): String {
        return DayOfWeek.of(day).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault())
    }
}