package com.deividasstr.ui.features.facts

import android.os.Bundle
import android.view.View
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentFactsBinding

class FactsFragment : BaseFragment<FragmentFactsBinding, FactsViewModel>() {

    override fun getViewModelClass(): Class<FactsViewModel> = FactsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_facts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewmodel = viewModel
        }
    }
}