package com.deividasstr.ui.features.facts

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.Observer
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.AnimationEndListener
import com.deividasstr.ui.base.framework.BaseFragment
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.models.FactUi
import com.deividasstr.ui.databinding.FragmentFactsBinding
import kotlinx.android.synthetic.main.fragment_facts.*

class FactsFragment : BaseFragment<FragmentFactsBinding, FactsViewModel>() {

    // Bounce down animation distance
    private val halfScreenHeight: Float by lazy {
        context?.resources?.displayMetrics?.heightPixels?.div(2f)!!
    }

    private lateinit var springAnimation: SpringAnimation
    private lateinit var transitionBottomAnimation: Animation

    private var isLaunch: Boolean = true

    override val fabSetter: FabSetter? = FabSetter(R.drawable.ic_refresh_white_24dp) {
        viewModel.getNewFact()
    }

    override fun getViewModelClass(): Class<FactsViewModel> = FactsViewModel::class.java

    override fun layoutId(): Int = R.layout.fragment_facts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpringAnimation()
        initTransitionAnimation()

        viewModel.fact.observe(this@FactsFragment, Observer { fact ->
            onNewFact(fact)
        })
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

    private fun initTransitionAnimation() {
        transitionBottomAnimation = AnimationUtils.loadAnimation(context, R.anim.transition_bottom)
        transitionBottomAnimation.setAnimationListener(object : AnimationEndListener {
            override fun onAnimationEnd() {
                fact.text = viewModel.fact.value?.text
                springAnimation.setStartValue(halfScreenHeight).start()
            }
        })
    }

    private fun onNewFact(fact: FactUi?) {
        // Skips slide down animation on launch - it looked like lag
        fact?.let {
            if (isLaunch) {
                isLaunch = false
                binding.fact.text = fact.text
                springAnimation.setStartValue(halfScreenHeight).start()
            } else {
                binding.fact.startAnimation(transitionBottomAnimation)
            }
        }
    }
}