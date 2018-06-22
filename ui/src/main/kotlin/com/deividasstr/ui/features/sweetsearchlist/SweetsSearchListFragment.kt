package com.deividasstr.ui.features.sweetsearchlist

import android.arch.paging.PagedList
import android.os.Bundle
import android.view.View
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.observe
import com.deividasstr.ui.base.framework.openKeyboard
import com.deividasstr.ui.base.framework.viewModel
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentSweetSearchListBinding

class SweetsSearchListFragment :
    BaseFragment<FragmentSweetSearchListBinding, SweetsSearchListViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_sweet_search_list

    private lateinit var viewModel: SweetsSearchListViewModel
    private val adapter: SweetsSearchAdapter by lazy { SweetsSearchAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance
        viewModel = viewModel(viewModelFactory) {
            observe(sweets, ::renderSweetsList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.sweetsList.setHasFixedSize(true)
        binding.sweetsList.adapter = adapter
        if (!viewModel.query.isEmpty()) {
            binding.sweetsSearchView.requestFocus()
            binding.sweetsSearchView.openKeyboard()
        }
    }

    private fun renderSweetsList(sweets: PagedList<SweetUi>?) {
        adapter.submitList(sweets)
    }
}