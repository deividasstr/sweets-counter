package com.deividasstr.ui.features.sweethistory

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentConsumedSweetHistoryBinding
import kotlinx.android.synthetic.main.fragment_consumed_sweet_history.*

class ConsumedSweetHistoryFragment : BaseFragment<FragmentConsumedSweetHistoryBinding, ConsumedSweetHistoryViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_consumed_sweet_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        period_details_fab.setOnClickListener {
            view.findNavController().navigate(R.id.action_consumedSweetHistoryFragment_to_sweetsSearchListFragment)
        }
    }
}