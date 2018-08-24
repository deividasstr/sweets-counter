package com.deividasstr.ui.features.consumedsweetdata.charts

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.github.mikephil.charting.animation.Easing
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

    val dataSet: PieDataSet

    if (view.data != null && view.data.dataSetCount > 0) {
        dataSet = view.data.getDataSetByIndex(0) as PieDataSet
        dataSet.values = entries
        view.data.notifyDataChanged()
        view.notifyDataSetChanged()
    } else {
        dataSet = PieDataSet(entries, "Total cals")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = colors

        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(11f)
        pieData.setValueTextColor(Color.BLACK)

        view.data = pieData
    }
    view.invalidate()
    view.animateY(400)
}

@BindingAdapter("android:setRatingData")
fun setRatingData(view: PieChart, data: Map<SweetRating, Long>?) {
    if (data == null) return

    val entries = mutableListOf<PieEntry>()

    for (sweet in data) {
        entries.add(PieEntry(sweet.value.toFloat(), sweet.key.name))
    }

    val colors = ChartColors.get()

    val dataSet: PieDataSet
    if (view.data != null && view.data.dataSetCount > 0) {
        dataSet = view.data.getDataSetByIndex(0) as PieDataSet
        dataSet.values = entries
        view.data.notifyDataChanged()
        view.notifyDataSetChanged()
    } else {
        dataSet = PieDataSet(entries, "Rating")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = colors
        dataSet.valueTextColor = Color.BLACK

        val pieData = PieData(dataSet)
        pieData.setValueFormatter(PercentFormatter())
        pieData.setValueTextSize(11f)
        view.data = pieData
    }
    view.invalidate()
    view.animateY(400)
}

@BindingAdapter("android:setConsumedData")
fun setConsumedData(view: BarChart, data: ConsumedBarData?) {
    if (data == null) return

    if (data.isEmpty) {
        view.data?.clearValues()
        view.invalidate()
    } else {
        val entries = mutableListOf<BarEntry>()

        data.calsPerTimeUnit.forEachIndexed { index, value ->
            entries.add(BarEntry(index.plus(1).toFloat(), value.toFloat()))
        }

        val dataSet: BarDataSet
        if (view.data != null && view.data.dataSetCount > 0) {
            dataSet = view.data.getDataSetByIndex(0) as BarDataSet
            dataSet.values = entries
            val xAxisFormatter = data.xAxisFormatterByRange()
            view.xAxis.valueFormatter = xAxisFormatter
            view.data.notifyDataChanged()
            view.notifyDataSetChanged()
        } else {
            val xAxisFormatter = data.xAxisFormatterByRange()
            val xAxis = view.xAxis
            with(xAxis) {
                position = XAxis.XAxisPosition.BOTTOM
                // typeface = mTfLight
                setDrawGridLines(false)
                valueFormatter = xAxisFormatter
            }

            val yAxisFormatter = YAxisFormatter()

            val leftYAxis = view.axisLeft
            leftYAxis.isEnabled = false
            leftYAxis.axisMinimum = 0f

            val rightYAxis = view.axisRight
            with(rightYAxis) {
                // setTypeface(mTfLight)
                valueFormatter = yAxisFormatter
                axisMinimum = 0f
            }

            view.rendererRightYAxis = YAxisSplitStringRenderer(
                view.viewPortHandler,
                view.axisRight,
                view.getTransformer(YAxis.AxisDependency.RIGHT))

            dataSet = BarDataSet(entries, "Consumed data")
            dataSet.colors = ChartColors.get()

            val dataSets = mutableListOf<IBarDataSet>()
            dataSets.add(dataSet)

            val barData = BarData(dataSets)

            with(barData) {
                // barData.setValueTextSize(10f)
                // barData.setValueTypeface(mTfLight)
                setValueFormatter(BarValueFormatter())
            }
            view.data = barData
        }
        view.invalidate()
        view.animateY(800, Easing.EasingOption.EaseInOutQuad)
        view.animateX(800, Easing.EasingOption.EaseInOutQuad)
    }
}

@BindingAdapter("android:chartListener")
fun setChartListener(view: BarChart, listener: OnChartValueSelectedListener?) {
    view.setOnChartValueSelectedListener(listener)
}
