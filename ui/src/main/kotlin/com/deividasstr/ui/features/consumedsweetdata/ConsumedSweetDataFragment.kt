package com.deividasstr.ui.features.consumedsweetdata

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentConsumedSweetDataBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class ConsumedSweetDataFragment :
    BaseFragment<FragmentConsumedSweetDataBinding, ConsumedSweetDataViewModel>(),
    OnChartValueSelectedListener {

    override fun getViewModelClass(): Class<ConsumedSweetDataViewModel> =
        ConsumedSweetDataViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_data

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewmodel = viewModel
            chartlistener = this@ConsumedSweetDataFragment
            setLifecycleOwner(this@ConsumedSweetDataFragment)

            val periods = listOf(
                getString(R.string.week),
                getString(R.string.month),
                getString(R.string.year))

            val adapter = ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_item,
                periods
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            consumedDataPeriodSpinner.adapter = adapter

            consumedDataSweetsPopularityChart.setUsePercentValues(true)
            consumedDataSweetsPopularityChart.animateY(800, Easing.EasingOption.EaseInOutQuad)

            consumedDataSweetsRatingChart.setUsePercentValues(true)
            consumedDataSweetsRatingChart.animateY(600, Easing.EasingOption.EaseInElastic)

            consumedDataPeriodChart.setDrawValueAboveBar(true)
            consumedDataPeriodChart.animateY(400)
            consumedDataPeriodChart.setScaleEnabled(false)
        }
    }

    override fun onNothingSelected() {
        viewModel.resetPeriod()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let { viewModel.timeUnitSelected(e.x.toInt()) }
    }
}