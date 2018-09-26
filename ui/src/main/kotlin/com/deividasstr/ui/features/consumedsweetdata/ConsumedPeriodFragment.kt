package com.deividasstr.ui.features.consumedsweetdata

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.observe
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.databinding.FragmentConsumedPeriodBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class ConsumedPeriodFragment :
    BaseFragment<FragmentConsumedPeriodBinding, ConsumedPeriodViewModel>(),
    OnChartValueSelectedListener {

    companion object {
        const val EXTRA_POS = "EXTRA_POS"
        fun newInstance(pos: Int): ConsumedPeriodFragment {
            return ConsumedPeriodFragment().apply {
                arguments = bundleOf(Pair(EXTRA_POS, pos))
            }
        }
    }

    private val pos: Int by lazy { arguments!!.getInt(EXTRA_POS) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        viewModel.pos = pos

        observe((parentFragment as ConsumedSweetDataFragment).sweets, ::addSweets)
        observe((parentFragment as ConsumedSweetDataFragment).currentPeriod, ::setPeriod)

        setBinding()
        setPies()

        return view
    }

    private fun setPies() {
        val typeface = ResourcesCompat.getFont(context!!, R.font.montserrat)!!
        with(binding) {
            setupPie(consumedDataSweetsPopularityChart, typeface)
            setupPie(consumedDataSweetsRatingChart, typeface)
        }
    }

    private fun addSweets(sweets: List<ConsumedSweetUi>) {
        viewModel.setSweets(sweets)
    }

    private fun setPeriod(period: Periods) {
        // if (viewModel.lowerPeriodModel.value != null) {
            viewModel.setPeriod(period)
    }

    private fun setBinding() {
        binding.viewmodel = viewModel
        binding.chartlistener = this@ConsumedPeriodFragment
    }

    private fun setupPie(view: PieChart, typeface: Typeface) {
        view.setNoDataTextTypeface(typeface)
        view.setCenterTextTypeface(typeface)
    }

    override fun getViewModelClass(): Class<ConsumedPeriodViewModel> =
        ConsumedPeriodViewModel::class.java

    override val fabSetter: FabSetter? = null

    override fun layoutId(): Int = R.layout.fragment_consumed_period

    override fun onNothingSelected() {
        viewModel.resetPeriod()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let { viewModel.toggleSubPeriod(e.x.toInt()) }
    }
}