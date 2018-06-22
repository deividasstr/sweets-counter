package com.deividasstr.ui.features.perioddetails

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentPeriodDetailsBinding
import kotlinx.android.synthetic.main.fragment_period_details.*

class PeriodDetailsFragment : BaseFragment<FragmentPeriodDetailsBinding, PeriodDetailsViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_period_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_details_fab.setOnClickListener {
            view.findNavController().navigate(R.id.action_list)
        }
    }
}