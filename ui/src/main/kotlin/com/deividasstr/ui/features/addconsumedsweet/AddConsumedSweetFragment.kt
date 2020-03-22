package com.deividasstr.ui.features.addconsumedsweet

import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.databinding.FragmentAddConsumedSweetBinding

class AddConsumedSweetFragment :
    BaseFragment<FragmentAddConsumedSweetBinding, AddConsumedSweetViewModel>() {

    override val fabSetter: FabSetter? = null

    override fun getViewModelClass(): Class<AddConsumedSweetViewModel> =
        AddConsumedSweetViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_add_consumed_sweet
}
