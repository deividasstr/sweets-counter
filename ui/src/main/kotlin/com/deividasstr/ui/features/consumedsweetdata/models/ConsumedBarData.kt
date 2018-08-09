package com.deividasstr.ui.features.consumedsweetdata.models

import com.deividasstr.domain.utils.Periods
import com.deividasstr.ui.features.consumedsweetdata.charts.MonthXAxisFormatter
import com.deividasstr.ui.features.consumedsweetdata.charts.WeekXAxisFormatter
import com.deividasstr.ui.features.consumedsweetdata.charts.YearXAxisFormatter
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.util.Arrays

data class ConsumedBarData(val calsPerTimeUnit: LongArray, val period: Periods) {

    fun xAxisFormatterByRange(): IAxisValueFormatter {
        return when (period) {
            Periods.WEEK -> WeekXAxisFormatter()
            Periods.MONTH -> MonthXAxisFormatter()
            Periods.YEAR -> YearXAxisFormatter()
            Periods.DAY -> throw
            IllegalArgumentException("DAY should not be passed to consumed data bar chart")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConsumedBarData

        if (!Arrays.equals(calsPerTimeUnit, other.calsPerTimeUnit)) return false
        if (period != other.period) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(calsPerTimeUnit)
        result = 31 * result + period.hashCode()
        return result
    }
}