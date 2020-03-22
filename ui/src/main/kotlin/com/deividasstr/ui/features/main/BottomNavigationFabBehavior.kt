package com.deividasstr.ui.features.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class BottomNavigationFabBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is Snackbar.SnackbarLayout || dependency is FloatingActionButton
    }

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        child.translationY = 0f
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return updateButton(child, dependency)
    }

    private fun updateButton(child: View, dependency: View): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            val oldTranslation = child.translationY
            val height = dependency.height.toFloat()
            val newTranslation = dependency.translationY - height
            child.translationY = newTranslation

            return oldTranslation != newTranslation
        }
        return false
    }
}
