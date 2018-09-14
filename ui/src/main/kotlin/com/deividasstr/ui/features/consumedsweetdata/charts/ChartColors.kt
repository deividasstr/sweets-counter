package com.deividasstr.ui.features.consumedsweetdata.charts

import com.github.mikephil.charting.utils.ColorTemplate

object ChartColors {
    fun get(): List<Int> {
        val colors = mutableListOf<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        return colors
    }
}
