package com.deividasstr.ui.features.consumedsweetdata.charts

import android.graphics.Canvas
import android.graphics.Paint
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.renderer.YAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.ViewPortHandler

class YAxisSplitStringRenderer(
    viewPortHandler: ViewPortHandler?,
    yAxis: YAxis?,
    trans: Transformer?
)
    : YAxisRenderer(viewPortHandler, yAxis, trans) {

    // Splits text at \n and draws 2 labels
    override fun drawYLabels(
        c: Canvas,
        fixedPosition: Float,
        positions: FloatArray,
        offset: Float
    ) {
        val from = if (mYAxis.isDrawBottomYLabelEntryEnabled) 0 else 1
        val to = if (mYAxis.isDrawTopYLabelEntryEnabled) {
            mYAxis.mEntryCount
        } else {
            mYAxis.mEntryCount - 1
        }

        mAxisLabelPaint.textAlign = Paint.Align.CENTER

        val textSize = mAxisLabelPaint.textSize
        val halfTextSize = textSize / 2
        for (i in from until to) {
            val text = mYAxis.getFormattedLabel(i)
            val splitTexts = text.split("\n")

            c.drawText(splitTexts[0], fixedPosition + textSize, positions[i * 2 + 1] + offset - halfTextSize, mAxisLabelPaint)
            c.drawText(splitTexts[1], fixedPosition + textSize, positions[i * 2 + 1] + offset + halfTextSize, mAxisLabelPaint)
        }
    }
}