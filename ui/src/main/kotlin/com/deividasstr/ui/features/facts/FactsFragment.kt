package com.deividasstr.ui.features.facts

import android.os.Bundle
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.viewModel
import com.deividasstr.ui.databinding.FragmentFactsBinding

class FactsFragment : BaseFragment<FragmentFactsBinding, FactsViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_facts

    lateinit var factsViewModel: FactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        factsViewModel = viewModel(viewModelFactory) {}
    }
}