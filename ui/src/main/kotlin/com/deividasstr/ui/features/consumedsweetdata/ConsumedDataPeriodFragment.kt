package com.deividasstr.ui.features.consumedsweetdata

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.databinding.FragmentConsumedPeriodBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class ConsumedDataPeriodFragment :
    BaseFragment<FragmentConsumedPeriodBinding, ConsumedDataPeriodViewModel>(),
    OnChartValueSelectedListener {

    override val fabSetter: FabSetter? = null

    companion object {
        const val EXTRA_POS = "EXTRA_POS"

        fun newInstance(pos: Int): ConsumedDataPeriodFragment {
            return ConsumedDataPeriodFragment().apply {
                arguments = bundleOf(Pair(EXTRA_POS, pos))
            }
        }
    }

    private val pos: Int by lazy { arguments!!.getInt(EXTRA_POS) }

    override fun getViewModelClass(): Class<ConsumedDataPeriodViewModel> =
        ConsumedDataPeriodViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_period

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("on created $pos")
        viewModel.pos = pos
        with(parentFragment as ConsumedSweetDataFragment) {
            sweets.observe(this@ConsumedDataPeriodFragment, Observer { it ->
                viewModel.setSweets(it)
                currentPeriod.observe(this@ConsumedDataPeriodFragment, Observer {
                    viewModel.setPeriod(it)
                })
            })
        }

        with(binding) {
            viewmodel = viewModel
            chartlistener = this@ConsumedDataPeriodFragment

            val typeface = ResourcesCompat.getFont(context!!, R.font.montserrat)!!

            val topLabel = getString(R.string.consumed_data_title_sweets_popularity)
            setupPie(consumedDataSweetsPopularityChart, typeface, "No data popularity", topLabel)

            val ratingLabel = getString(R.string.consumed_data_title_sweets_rating_title)
            setupPie(consumedDataSweetsRatingChart, typeface, "No data Rating", ratingLabel)

            setupBar(consumedDataPeriodChart, typeface, "No data")
        }
    }

    private fun setupPie(
        view: PieChart,
        typeface: Typeface,
        noDataText: String,
        labelText: String
    ) {
        view.setUsePercentValues(true)
        view.setNoDataText(noDataText)
        view.legend.isEnabled = false
        view.setEntryLabelColor(android.graphics.Color.BLACK)
        view.description.isEnabled = false
        // view.isRotationEnabled = false
        view.setNoDataTextTypeface(typeface)
        view.centerText = labelText
        view.setCenterTextTypeface(typeface)
        view.setCenterTextSize(16f)
    }

    private fun setupBar(view: BarChart, typeface: Typeface, noDataText: String) {
        view.setDrawValueAboveBar(true)
        view.setScaleEnabled(false)
        view.legend.isEnabled = false
        view.description.isEnabled = false
        view.setNoDataText(noDataText)
        view.setNoDataTextTypeface(typeface)
    }

    override fun onNothingSelected() {
        viewModel.resetPeriod()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let { viewModel.barClicked(e.x.toInt()) }
    }
}