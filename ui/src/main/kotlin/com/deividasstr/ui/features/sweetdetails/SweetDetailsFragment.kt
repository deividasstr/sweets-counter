package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseActivity
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.closeKeyboard
import com.deividasstr.ui.base.framework.extensions.openKeyboard
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentSweetDetailsBinding
import kotlinx.android.synthetic.main.fragment_sweet_details.*

@DebugOpenClass
class SweetDetailsFragment : BaseFragment<FragmentSweetDetailsBinding, SweetDetailsViewModel>() {

    companion object {
        const val EXTRA_ENTERED_VAL = "EXTRA_ENTERED_VAL"
    }

    private val sweet: SweetUi by lazy { SweetDetailsFragmentArgs.fromBundle(arguments!!).sweet }

    private val ratingClickListener: View.OnClickListener =
        View.OnClickListener {
            RatingInfoDialogFragment().show(childFragmentManager, null)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setSweet(sweet)
        ViewCompat.setTransitionName(sweet_name, sweet.name)
        setBinding()
        openKeyboard()
        (activity as BaseActivity).liftNavBar()
    }

    private fun setBinding() {
        binding.viewmodel = viewModel
        binding.ratingclicklistener = ratingClickListener
    }

    private fun openKeyboard() {
        consumed_sweet_view.requestFocus()
        consumed_sweet_view.openKeyboard()
    }

    private fun consumeSweet() {
        val navigationCallback = object : NavigationCallback {
            override fun onNavigate() {
                consumed_sweet_view.closeKeyboard()
                navController()
                    .navigate(R.id.action_sweetDetailsFragment_to_consumedSweetHistoryFragment)
            }
        }
        viewModel.validate(navigationCallback)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EXTRA_ENTERED_VAL, viewModel.enteredValue.value)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getString(EXTRA_ENTERED_VAL)?.let {
            viewModel.restore(it)
        }
    }

    override val fabSetter: FabSetter? = FabSetter(R.drawable.ic_done_white_24dp) { consumeSweet() }

    override fun getViewModelClass(): Class<SweetDetailsViewModel> =
        SweetDetailsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_details
}