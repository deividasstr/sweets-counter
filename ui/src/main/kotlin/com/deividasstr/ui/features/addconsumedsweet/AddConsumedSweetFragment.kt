package com.deividasstr.ui.features.addconsumedsweet

import android.os.Bundle
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentAddConsumedSweetBinding

class AddConsumedSweetFragment : BaseFragment<FragmentAddConsumedSweetBinding, AddConsumedSweetViewModel>() {

    override fun getViewModelClass(): Class<AddConsumedSweetViewModel> = AddConsumedSweetViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_add_consumed_sweet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}