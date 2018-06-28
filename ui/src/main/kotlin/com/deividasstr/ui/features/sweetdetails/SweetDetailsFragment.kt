package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.databinding.FragmentSweetDetailsBinding

class SweetDetailsFragment : BaseFragment<FragmentSweetDetailsBinding, SweetDetailsViewModel>() {

    override fun getViewModelClass(): Class<SweetDetailsViewModel> = SweetDetailsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_details

    private var sweetId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sweetId = SweetDetailsFragmentArgs.fromBundle(arguments).sweetId.toLong()
    }
}