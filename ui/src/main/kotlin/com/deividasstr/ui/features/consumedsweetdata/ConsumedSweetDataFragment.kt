package com.deividasstr.ui.features.consumedsweetdata

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.deividasstr.domain.enums.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentConsumedSweetDataBinding
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts

class ConsumedSweetDataFragment :
    BaseFragment<FragmentConsumedSweetDataBinding, ConsumedSweetDataViewModel>() {

    override val fabSetter: FabSetter? = null

    lateinit var sweets: LiveData<Pair<List<ConsumedSweetUi>, List<SweetUi>>>
    lateinit var currentPeriod: LiveData<Periods>

    override fun getViewModelClass(): Class<ConsumedSweetDataViewModel> =
        ConsumedSweetDataViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_data

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sweets = viewModel.sweetsPair
        currentPeriod = viewModel.currentPeriod
        currentPeriod.observe(this, Observer {
            binding.consumedDetailsViewpager.currentItem = Consts.FIRST_ITEM
        })
        with(binding) {
            viewmodel = viewModel

            consumedDetailsViewpager.adapter = PeriodPagerAdapter(childFragmentManager)
            consumedDetailsViewpager.currentItem = Consts.FIRST_ITEM

            consumedDataPeriodSpinner.adapter = periodsSpinnerAdapter()
        }
    }

    private fun periodsSpinnerAdapter(): SpinnerAdapter? {
        val periods = listOf(
            getString(R.string.week),
            getString(R.string.month),
            getString(R.string.year))

        return ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_item,
            periods
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }
}