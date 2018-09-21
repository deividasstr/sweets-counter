package com.deividasstr.ui.features.sweetsearchlist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseActivity
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.closeKeyboard
import com.deividasstr.ui.base.framework.extensions.observe
import com.deividasstr.ui.base.framework.extensions.openKeyboard
import com.deividasstr.ui.base.framework.sharedelements.HasSharedElements
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentSweetSearchListBinding
import com.deividasstr.ui.features.sweetsearchlist.SweetsSearchListFragmentDirections.actionSweetDetails

class SweetsSearchListFragment :
    BaseFragment<FragmentSweetSearchListBinding, SweetsSearchListViewModel>(), HasSharedElements {

    companion object {
        const val EXTRA_ENTERED_VAL = "EXTRA_ENTERED_VAL"
        const val EXTRA_RECYCLER_POS = "EXTRA_RECYCLER_POS"
    }

    private val adapter: SweetsSearchAdapter by lazy { createAdapter() }
    private val sharedElements: MutableMap<String, View> = mutableMapOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        initRecycler()
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

    private fun initRecycler() {
        with(binding) {
            sweetsRecycler.setHasFixedSize(true)

            val itemDecor =
                DividerItemDecoration(sweetsRecycler.context, DividerItemDecoration.VERTICAL)
            sweetsRecycler.addItemDecoration(itemDecor)
            sweetsRecycler.adapter = adapter
        }
    }

    private fun renderSweetsList(sweets: PagedList<SweetUi>?) {
        if (sweets != null && sweets.isEmpty() && viewModel.query.isEmpty()) {
            binding.sweetsSearchView.closeKeyboard()
            (activity as BaseActivity).alert(R.string.no_sweets_available)
        } else {
            adapter.submitList(sweets)
        }
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

    private fun createAdapter(): SweetsSearchAdapter {
        val adapter = SweetsSearchAdapter()
        adapter.clickListener = { sweet, view ->
            navigateToSweetDetails(sweet, view)
        }
        return adapter
    }

    override fun getSharedElements(): Map<String, View> = sharedElements

    override fun hasReorderingAllowed(): Boolean = false

    override val fabSetter: FabSetter? = null

    override fun getViewModelClass(): Class<SweetsSearchListViewModel> =
        SweetsSearchListViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_search_list

    override fun onSaveInstanceState(outState: Bundle) {
        val currPos = (binding.sweetsRecycler.layoutManager as LinearLayoutManager)
            .findFirstCompletelyVisibleItemPosition()

        outState.putString(EXTRA_ENTERED_VAL, viewModel.query)
        outState.putInt(EXTRA_RECYCLER_POS, currPos)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            val enteredVal = it.getString(EXTRA_ENTERED_VAL)
            if (!enteredVal.isNullOrEmpty()) viewModel.searchSweets(enteredVal!!)

            val pos = it.getInt(EXTRA_RECYCLER_POS)
            if (pos > 0) binding.sweetsRecycler.smoothScrollToPosition(pos + 4)
        }
    }
}