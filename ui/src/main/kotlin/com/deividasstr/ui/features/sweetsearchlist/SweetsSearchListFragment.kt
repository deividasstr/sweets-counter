package com.deividasstr.ui.features.sweetsearchlist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.paging.PagedList
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.observe
import com.deividasstr.ui.base.framework.openKeyboard
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentSweetSearchListBinding

class SweetsSearchListFragment :
    BaseFragment<FragmentSweetSearchListBinding, SweetsSearchListViewModel>() {

    override fun getViewModelClass(): Class<SweetsSearchListViewModel> =
        SweetsSearchListViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_search_list

    private val adapter: SweetsSearchAdapter by lazy { createAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewmodel = viewModel
            sweetsList.setHasFixedSize(true)
            sweetsList.adapter = adapter
            if (queriedBefore) {
                sweetsSearchView.requestFocus()
                sweetsSearchView.openKeyboard()
            }
        }
        observe(viewModel.sweets, ::renderSweetsList)
    }

    private fun navigateToSweetDetails(sweetId: Long) {
        val action = SweetsSearchListFragmentDirections.action_sweet_details()
        action.setSweetId(sweetId.toInt())
        findNavController(this).navigate(action)
    }

    private val queriedBefore: Boolean
        get() =  !viewModel.query.isEmpty()

    private fun renderSweetsList(sweets: PagedList<SweetUi>?) {
        adapter.submitList(sweets)
    }

    private fun createAdapter(): SweetsSearchAdapter {
        val adapter = SweetsSearchAdapter()
        adapter.clickListener = { sweetId ->
            navigateToSweetDetails(sweetId)
        }
        return adapter
    }
}