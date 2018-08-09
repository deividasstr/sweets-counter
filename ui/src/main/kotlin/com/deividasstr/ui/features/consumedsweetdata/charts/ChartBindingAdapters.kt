package com.deividasstr.ui.features.consumedsweetdata.charts

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.github.mikephil.charting.charts.BarChart
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

    for (sweet in data) {
        entries.add(PieEntry(sweet.consumedG.toFloat(), sweet.sweet.name))
    }

    val colors = ChartColors.get()

    val dataSet = PieDataSet(entries, "Total cals")
    dataSet.sliceSpace = 3f
    dataSet.selectionShift = 5f
    dataSet.colors = colors

    val pieData = PieData(dataSet)
    pieData.setValueFormatter(PercentFormatter())
    pieData.setValueTextSize(11f)
    pieData.setValueTextColor(Color.WHITE)

    view.data = pieData
    view.invalidate()
}

@BindingAdapter("android:setRatingData")
fun setRatingData(view: PieChart, data: Map<SweetRating, Int>?) {

    if (data == null) return

    val entries = mutableListOf<PieEntry>()

    for (sweet in data) {
        entries.add(PieEntry(sweet.value.toFloat(), sweet.key.name))
    }

    val colors = ChartColors.get()

    val dataSet = PieDataSet(entries, "Rating")
    dataSet.sliceSpace = 3f
    dataSet.selectionShift = 5f
    dataSet.colors = colors

    val pieData = PieData(dataSet)
    pieData.setValueFormatter(PercentFormatter())
    pieData.setValueTextSize(11f)
    pieData.setValueTextColor(Color.WHITE)

    view.data = pieData
    view.invalidate()
}

@BindingAdapter("android:setConsumedData")
fun setConsumedData(view: BarChart, data: ConsumedBarData?) {
    if (data == null) return

    println("data $data")

    val entries = mutableListOf<BarEntry>()

    data.calsPerTimeUnit.forEachIndexed { index, value ->
        entries.add(BarEntry(index.plus(1).toFloat(), value.toFloat()))
    }

    val xAxisFormatter = data.xAxisFormatterByRange()

    val xAxis = view.xAxis
    with(xAxis) {
        position = XAxis.XAxisPosition.BOTTOM
        //typeface = mTfLight
        setDrawGridLines(false)
        granularity = 1f // only intervals of 1 day
        //labelCount = 7
        valueFormatter = xAxisFormatter
    }

    val yAxisFormatter = YAxisFormatter()

    val leftAxis = view.axisLeft
    //leftAxis.setTypeface(mTfLight)
    with(leftAxis) {
        //setLabelCount(8, false)
        valueFormatter = yAxisFormatter
        setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        spaceTop = 15f
        axisMinimum = 0f // this replaces setStartAtZero(true)
    }

    val rightAxis = view.axisRight
    with(rightAxis) {
        setDrawGridLines(false)
        //setTypeface(mTfLight)
        //setLabelCount(8, false)
        valueFormatter = yAxisFormatter
        spaceTop = 15f
        axisMinimum = 0f // this replaces setStartAtZero(true)
    }

    val set = BarDataSet(entries, "Consumed data")
    set.colors = ChartColors.get()

    val dataSets = mutableListOf<IBarDataSet>()
    dataSets.add(set)

    val barData = BarData(dataSets)

    with(barData) {
        //barData.setValueTextSize(10f)
        //barData.setValueTypeface(mTfLight)
        barData.barWidth = 0.9f
    }

    view.data = barData
    view.invalidate()
}

@BindingAdapter("android:chartListener")
fun setChartListener(view: BarChart, listener: OnChartValueSelectedListener?) {
    view.setOnChartValueSelectedListener(listener)
}

