package com.deividasstr.ui.features.consumedsweetdata.charts

import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.deividasstr.ui.R
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@BindingAdapter("android:setPopularityData")
fun setPopularityData(view: PieChart, data: List<PopularitySweetUi>?) {
    if (data == null) return
    val entries = mutableListOf<PieEntry>()

    data.forEach { sweet ->
        // If item is 10th, name it "Other"
        if (sweet.name == "OTHER") {
            val name = view.context.getString(R.string.top_sweets_pie_other)
            entries.add(PieEntry(sweet.consumedG.toFloat(), name))
        } else {
            entries.add(PieEntry(sweet.consumedG.toFloat(), sweet.name))
        }
    }

    if (chartPrepared(view)) {
        refreshPieData(view, entries)
    } else {
        preparePie(entries, view, true)
    }
    view.invalidate()
    view.animateY(400)
    // view.animateY(400, Easing.EasingOption.EaseInQuad)
}

private fun chartPrepared(view: Chart<*>) =
    view.data != null && view.data.dataSetCount > 0

@BindingAdapter("android:setRatingData")
fun setRatingData(view: PieChart, data: Map<SweetRating, Long>?) {
    if (data == null) return

    val entries = mutableListOf<PieEntry>()

    for (sweet in data) {
        val name = view.context.getString(sweet.key.nameStringRes())
        entries.add(PieEntry(sweet.value.toFloat(), name))
    }

    if (view.data != null && view.data.dataSetCount > 0) {
        refreshPieData(view, entries)
    } else {
        preparePie(entries, view, false)
    }
    view.invalidate()
    view.animateY(400)
}

private fun refreshPieData(
    view: PieChart,
    entries: MutableList<PieEntry>
) {
    val dataSet: PieDataSet = view.data.getDataSetByIndex(0) as PieDataSet
    dataSet.values = entries
    view.data.notifyDataChanged()
    view.notifyDataSetChanged()
}

private fun preparePie(entries: MutableList<PieEntry>, view: PieChart, pointedVals: Boolean) {

    val typeface = ResourcesCompat.getFont(view.context, R.font.montserrat)
    val colors = ChartColors.get()

    val dataSet = PieDataSet(entries, null)
    dataSet.sliceSpace = 3f
    dataSet.selectionShift = 5f
    dataSet.colors = colors
    dataSet.valueTextColor = Color.BLACK
    dataSet.valueTypeface = typeface

    if (pointedVals) {
        dataSet.valueLinePart1OffsetPercentage = 70f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.2f
        dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.yValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
    }

    val pieData = PieData(dataSet)
    pieData.setValueFormatter(PercentFormatter())
    pieData.setValueTextSize(11f)
    pieData.setValueTypeface(typeface)

    view.data = pieData
    view.data.dataSet
}

@BindingAdapter("android:setConsumedData")
fun setConsumedData(view: BarChart, data: ConsumedBarData?) {
    if (data == null) return

    val entries = mutableListOf<BarEntry>()

    data.calsPerTimeUnit.forEachIndexed { index, value ->
        entries.add(BarEntry(index.plus(1).toFloat(), value.toFloat()))
    }

    if (chartPrepared(view)) {
        refreshBar(view, entries, data)
    } else {
        prepareBar(view, data, entries)
    }
    view.invalidate()
    view.animateY(800)
}

fun prepareBar(
    view: BarChart,
    data: ConsumedBarData,
    entries: MutableList<BarEntry>
) {
    val typeface = ResourcesCompat.getFont(view.context, R.font.montserrat)

    val xAxisFormatter = data.xAxisFormatterByRange()
    val labelCount = data.xAxisLabelCountByRange()

    val xAxis = view.xAxis
    with(xAxis) {
        position = XAxis.XAxisPosition.BOTTOM
        this.typeface = typeface
        setDrawGridLines(false)
        this.labelCount = labelCount
        valueFormatter = xAxisFormatter
    }

    val leftYAxis = view.axisLeft
    leftYAxis.axisMinimum = 0f
    leftYAxis.setDrawLabels(false)

    val yAxisFormatter = YAxisFormatter()
    val rightYAxis = view.axisRight
    with(rightYAxis) {
        this.typeface = typeface
        valueFormatter = yAxisFormatter
        axisMinimum = 0f
    }

    view.rendererRightYAxis = YAxisSplitStringRenderer(
        view.viewPortHandler,
        view.axisRight,
        view.getTransformer(YAxis.AxisDependency.RIGHT))

    val dataSet = BarDataSet(entries, "Consumed data")
    dataSet.colors = ChartColors.get()

    val dataSets = mutableListOf<IBarDataSet>()
    dataSets.add(dataSet)

    val barData = BarData(dataSets)

    with(barData) {
        barData.setValueTextSize(10f)
        barData.setValueTypeface(typeface)
        setValueFormatter(BarValueFormatter())
    }

    view.data = barData
}

private fun refreshBar(
    view: BarChart,
    entries: MutableList<BarEntry>,
    data: ConsumedBarData
) {
    val xAxisFormatter = data.xAxisFormatterByRange()
    val labelCount = data.xAxisLabelCountByRange()

    view.xAxis.labelCount = labelCount
    view.xAxis.valueFormatter = xAxisFormatter

    val dataSet = view.data.getDataSetByIndex(0) as BarDataSet
    dataSet.values = entries

    view.data.notifyDataChanged()
    view.notifyDataSetChanged()
}

@BindingAdapter("android:chartListener")
fun setChartListener(view: BarChart, listener: OnChartValueSelectedListener?) {
    view.setOnChartValueSelectedListener(listener)
}
