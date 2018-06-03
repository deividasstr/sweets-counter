package com.deividasstr.ui.features.sweetsearchlist

import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentSweetSearchListBinding

class SweetsSearchListFragment :
    BaseFragment<FragmentSweetSearchListBinding, SweetsSearchListViewModel>() {

    override fun layoutId(): Int = R.layout.fragment_sweet_search_list
}