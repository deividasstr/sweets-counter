package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.transition.TransitionInflater
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.closeKeyboard
import com.deividasstr.ui.base.framework.openKeyboard
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.databinding.FragmentSweetDetailsBinding
import kotlinx.android.synthetic.main.sweet_details_add_sweet.*

@DebugOpenClass
class SweetDetailsFragment : BaseFragment<FragmentSweetDetailsBinding, SweetDetailsViewModel>() {

    companion object {
        const val EXTRA_ENTERED_VAL = "EXTRA_ENTERED_VAL"
    }

    override val fabSetter: FabSetter? = FabSetter(R.drawable.ic_done_white_24dp) { consumeSweet() }

    override fun getViewModelClass(): Class<SweetDetailsViewModel> =
        SweetDetailsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_sweet_details

    private val sweet: SweetUi by lazy {
        SweetDetailsFragmentArgs.fromBundle(arguments).sweet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ViewCompat.setTransitionName(sweetName, sweet.name)
            viewModel.setSweet(sweet)

            viewmodel = viewModel
            ratingclicklistener = ratingClickListener

            consumed_sweet_view.requestFocus()
            consumed_sweet_view.openKeyboard()
        }
        (activity as BaseActivity).liftNavBar()
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

    private val ratingClickListener: View.OnClickListener =
        View.OnClickListener {
            RatingInfoDialogFragment().show(childFragmentManager, null)
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
}