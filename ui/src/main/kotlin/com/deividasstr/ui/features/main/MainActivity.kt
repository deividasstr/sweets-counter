package com.deividasstr.ui.features.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.AnimationEndListener
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.base.framework.base.BaseActivity
import com.deividasstr.ui.base.framework.base.viewModel
import com.deividasstr.ui.base.framework.extensions.hide
import com.deividasstr.ui.base.framework.extensions.show
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

        mainActivityViewModel = viewModel(viewModelFactory) {
            errorMessage.observe(this@MainActivity, errorObserver)
            tryDownloadSweets()
            tryDownloadFacts()
        }

        handleSplash(savedInstanceState)
        handleKeyBoardApparition()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragment_container)
        setupWithNavController(bottom_navigation, navController)
    }

    // Does not play animation when activity is recovering from kill
    private fun handleSplash(savedInstanceState: Bundle?) {
        savedInstanceState?.let { splash.hide() } ?: zoomOutSplash()
    }

    private fun handleKeyBoardApparition() {
        container.viewTreeObserver.addOnGlobalLayoutListener {
            fab.translationY = (-fabYTranslationByKeyboard()).toFloat()
        }
    }

    private fun fabYTranslationByKeyboard(): Int {
        val visibleWindow = Rect()
        container.getWindowVisibleDisplayFrame(visibleWindow)

        return if (isKeyboardOpen(visibleWindow)) {
            keyboardHeight(visibleWindow)
        } else {
            0
        }
    }

    private fun keyboardHeight(windowAboveKeyboard: Rect) =
        fab.bottom - windowAboveKeyboard.bottom

    private fun isKeyboardOpen(windowAboveKeyboard: Rect) =
        container.bottom > windowAboveKeyboard.bottom

    private fun zoomOutSplash() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        animation.setAnimationListener(object : AnimationEndListener {
            override fun onAnimationEnd() {
                splash.hide()
            }
        })
        splash.startAnimation(animation)
    }

    private val errorObserver = Observer<SingleEvent<Error>> { it ->
        it?.getContentIfNotHandled()?.let {
            alert(it.stringRes)
        }
    }

    override fun setFab(fabSetter: FabSetter?) {
        if (fabSetter != null) {
            fab.setOnClickListener { fabSetter.onClick.invoke() }
            showFab(fabSetter)
        } else {
            fab.setOnClickListener { }
            hideFab()
        }
    }

    private fun hideFab() {
        fab.animate().apply {
            cancel()
            alpha(0f)
            duration = 0
            withEndAction { (fab as View).hide() }
        }
    }

    private fun showFab(fabSetter: FabSetter) {
        fab.animate().apply {
            cancel()
            duration = resources.getInteger(R.integer.duration_animation_short).toLong()

            val snackbar = container.children.firstOrNull { it is Snackbar.SnackbarLayout }
            if (snackbar != null && fab.translationY == 0f) {
                val height = snackbar.height.toFloat()
                translationY(-height)
            }
            fab.setImageResource(fabSetter.srcRes)
            alpha(1f)
            (fab as View).show()
        }
    }

    override fun liftNavBar() {
        if (bottom_navigation.translationY > 0f) bottom_navigation.translationY = 0f
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments[0].childFragmentManager.backStackEntryCount == 1) {
            val menu = bottom_navigation.menu
            menu.getItem(0).isChecked = true
            bottom_navigation.onNavigationItemSelected(menu.getItem(0))
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragment_container).navigateUp()
    }
}