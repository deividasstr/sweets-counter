package com.deividasstr.ui.base.framework

import android.view.animation.Animation
typealias AnimEnd = () -> Unit

interface AnimationEndListener : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) { onAnimationEnd() }

    override fun onAnimationStart(animation: Animation?) {}

    fun onAnimationEnd()
}
