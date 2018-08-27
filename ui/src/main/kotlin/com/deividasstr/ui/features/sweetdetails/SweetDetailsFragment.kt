package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import android.view.View
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.closeKeyboard
import com.deividasstr.ui.base.framework.openKeyboard
import com.deividasstr.ui.databinding.FragmentSweetDetailsBinding
import kotlinx.android.synthetic.main.sweet_details_add_sweet.*

@DebugOpenClass
class SweetDetailsFragment : BaseFragment<FragmentSweetDetailsBinding, SweetDetailsViewModel>() {

    override fun getViewModelClass(): Class<SweetDetailsViewModel> =
        SweetDetailsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_details

    private val sweetId: Long by lazy {
        SweetDetailsFragmentArgs.fromBundle(arguments).sweetId.toLong()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.sweetId = sweetId
            viewmodel = viewModel
            clickListener = View.OnClickListener {
                onConsumeSweet()
            }
            consumed_sweet_view.closeKeyboard()
            consumed_sweet_view.requestFocus()
            consumed_sweet_view.openKeyboard()
        }
    }

    private fun onConsumeSweet() {
        val navigationCallback = object : NavigationCallback {
            override fun onNavigate() {
                view?.closeKeyboard()
                navController().navigate(R.id.action_sweetDetailsFragment_to_consumedSweetHistoryFragment)
            }
        }
        viewModel.validate(navigationCallback)
    }
}