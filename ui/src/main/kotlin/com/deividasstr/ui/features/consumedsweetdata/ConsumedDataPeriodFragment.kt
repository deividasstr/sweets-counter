package com.deividasstr.ui.features.consumedsweetdata

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentConsumedPeriodBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class ConsumedDataPeriodFragment :
    BaseFragment<FragmentConsumedPeriodBinding, ConsumedDataPeriodViewModel>(),
    OnChartValueSelectedListener {

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

            consumedDataSweetsPopularityChart.setUsePercentValues(true)
            consumedDataSweetsPopularityChart.setNoDataText("No data popularity")
            consumedDataSweetsPopularityChart.legend.isEnabled = false
            consumedDataSweetsPopularityChart.setEntryLabelColor(android.graphics.Color.BLACK)
            consumedDataSweetsPopularityChart.description.isEnabled = false
            consumedDataSweetsPopularityChart.isRotationEnabled = false
            consumedDataSweetsPopularityChart.centerText =
                getString(R.string.consumed_data_title_sweets_popularity)

            consumedDataSweetsRatingChart.setUsePercentValues(true)
            consumedDataSweetsRatingChart.setNoDataText("No data rating")
            consumedDataSweetsRatingChart.legend.isEnabled = false
            consumedDataSweetsRatingChart.setEntryLabelColor(android.graphics.Color.BLACK)
            consumedDataSweetsRatingChart.description.isEnabled = false
            consumedDataSweetsRatingChart.isRotationEnabled = false
            consumedDataSweetsRatingChart.centerText =
                getString(R.string.consumed_data_title_sweets_rating_title)

            consumedDataPeriodChart.setDrawValueAboveBar(true)
            consumedDataPeriodChart.setScaleEnabled(false)
            consumedDataPeriodChart.legend.isEnabled = false
            consumedDataPeriodChart.description.isEnabled = false
            consumedDataPeriodChart.setNoDataText("No data bar")
        }
    }

    override fun onNothingSelected() {
        viewModel.resetPeriod()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let { viewModel.barClicked(e.x.toInt()) }
    }
}