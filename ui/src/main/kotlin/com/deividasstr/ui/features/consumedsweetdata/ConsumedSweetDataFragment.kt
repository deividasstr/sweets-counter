package com.deividasstr.ui.features.consumedsweetdata

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.lifecycle.LiveData
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.observe
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.databinding.FragmentConsumedSweetDataBinding
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts

class ConsumedSweetDataFragment :
    BaseFragment<FragmentConsumedSweetDataBinding, ConsumedSweetDataViewModel>() {

    lateinit var sweets: LiveData<List<ConsumedSweetUi>>
    lateinit var currentPeriod: LiveData<Periods>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sweets = viewModel.consumedSweets
        currentPeriod = viewModel.currentPeriod
        observe(currentPeriod, ::refreshPeriod)

        with(binding) {
            viewmodel = viewModel
            consumedDataPeriodSpinner.adapter = periodsSpinnerAdapter()
        }
        setViewPager()
    }

    private fun setViewPager() {
        binding.consumedDetailsViewpager.adapter = PeriodPagerAdapter(childFragmentManager)
        binding.consumedDetailsViewpager.currentItem = Consts.FIRST_ITEM
    }

    private fun refreshPeriod(periods: Periods?) {
        binding.consumedDetailsViewpager.currentItem = Consts.FIRST_ITEM
    }

    private fun periodsSpinnerAdapter(): SpinnerAdapter? {
        val periods = listOf(
            getString(R.string.week),
            getString(R.string.month),
            getString(R.string.year))

        return ArrayAdapter(context!!, android.R.layout.simple_spinner_item, periods)
            .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    override fun getViewModelClass(): Class<ConsumedSweetDataViewModel> =
        ConsumedSweetDataViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_data

    override val fabSetter: FabSetter? = null
}
