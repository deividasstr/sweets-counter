package com.deividasstr.ui.base.framework

import android.view.animation.Animation

interface AnimationEndListener : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {}

    override fun onAnimationEnd(animation: Animation?) {}

    override fun onAnimationStart(animation: Animation?) {
        onAnimationEnd()
    }

    fun onAnimationEnd()
}