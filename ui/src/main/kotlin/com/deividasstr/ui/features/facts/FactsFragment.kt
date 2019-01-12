package com.deividasstr.ui.features.facts

import android.os.Bundle
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseFragment
import com.deividasstr.ui.base.framework.extensions.observe
import com.deividasstr.ui.base.models.FactUi
import com.deividasstr.ui.databinding.FragmentFactsBinding
import kotlinx.android.synthetic.main.fragment_facts.*

class FactsFragment : BaseFragment<FragmentFactsBinding, FactsViewModel>() {

    // Bounce down animation distance
    private val halfScreenHeight: Float by lazy {
        context?.resources?.displayMetrics?.heightPixels?.div(2f) ?: 0f
    }

    private lateinit var springAnimation: SpringAnimation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpringAnimation()
        binding.viewmodel = viewModel
        observe(viewModel.liveFact, ::onNewFact)
    }

    private fun initSpringAnimation() {
        springAnimation = SpringAnimation(fact, DynamicAnimation.TRANSLATION_Y).apply {
            val spring = SpringForce()
            spring.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            spring.stiffness = SpringForce.STIFFNESS_LOW
            spring.finalPosition = 0f
            setSpring(spring)
            setStartValue(halfScreenHeight)
        }
    }

    private fun onNewFact(fact: FactUi?) {
        fact?.let {
            springAnimation.setStartValue(halfScreenHeight).start()
        }
    }

    override val fabSetter: FabSetter? = FabSetter(R.drawable.ic_refresh_white_24dp) {
        viewModel.getNewFact()
    }

    override fun getViewModelClass(): Class<FactsViewModel> = FactsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_facts
}