package com.deividasstr.ui.features.sweetsearchlist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.closeKeyboard
import com.deividasstr.ui.base.framework.observe
import com.deividasstr.ui.base.framework.openKeyboard
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.sharedelements.HasSharedElements
import com.deividasstr.ui.databinding.FragmentSweetSearchListBinding
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListFragmentDirections.actionSweetDetails

class SweetsSearchListFragment :
    BaseFragment<FragmentSweetSearchListBinding, SweetsSearchListViewModel>(), HasSharedElements {

    override val fabSetter: FabSetter? = null

    private val sharedElements: MutableMap<String, View> = mutableMapOf()

    override fun getViewModelClass(): Class<SweetsSearchListViewModel> =
        SweetsSearchListViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_search_list

    private val adapter: SweetsSearchAdapter by lazy { createAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewmodel = viewModel
            sweetsRecycler.setHasFixedSize(true)

            val itemDecor = DividerItemDecoration(sweetsRecycler.context, VERTICAL)
            sweetsRecycler.addItemDecoration(itemDecor)
            sweetsRecycler.adapter = adapter
        }
        observe(viewModel.sweets, ::renderSweetsList)
    }

    override fun onResume() {
        super.onResume()
        binding.sweetsSearchView.requestFocus()
        binding.sweetsSearchView.openKeyboard()
    }

    override fun onPause() {
        binding.sweetsSearchView.closeKeyboard()
        super.onPause()
    }

    private fun navigateToSweetDetails(sweet: SweetUi, view: TextView) {
        setSharedElement(view)

        val action = actionSweetDetails(sweet)
        Navigation.findNavController(view).navigate(action)
    }

    private fun setSharedElement(view: TextView) {
        sharedElements.clear()
        sharedElements[ViewCompat.getTransitionName(view)!!] = view
    }

    private fun renderSweetsList(sweets: PagedList<SweetUi>?) {
        if (sweets != null && sweets.isEmpty() && viewModel.query.isEmpty()) {
            (activity as BaseActivity).alert(R.string.no_sweets_available)
        } else {
            adapter.submitList(sweets)
        }
    }

    private fun createAdapter(): SweetsSearchAdapter {
        val adapter = SweetsSearchAdapter()
        adapter.clickListener = { sweet, view ->
            navigateToSweetDetails(sweet, view)
        }
        return adapter
    }

    override fun getSharedElements(): Map<String, View> = sharedElements

    override fun hasReorderingAllowed(): Boolean = false
}