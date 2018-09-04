package com.deividasstr.ui.features.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.deividasstr.data.utils.StringResException
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.AnimationEndListener
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.base.framework.alert
import com.deividasstr.ui.base.framework.hide
import com.deividasstr.ui.base.framework.show
import com.deividasstr.ui.base.framework.viewModel
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

    // Does not play animation when activity is recovering from kill
    private fun handleSplash(savedInstanceState: Bundle?) {
        savedInstanceState?.let { splash.hide() } ?: zoomOutSplash()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.fragment_container)
        setupWithNavController(bottom_navigation, navController)
    }

    private fun zoomOutSplash() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.zoom_in)
        animation.setAnimationListener(object : AnimationEndListener {
            override fun onAnimationEnd() {
                splash.hide()
            }
        })
        splash.startAnimation(animation)
    }

    private val errorObserver = Observer<SingleEvent<Throwable>> { it ->
        it?.getContentIfNotHandled()?.let {
            if (it is StringResException) {
                alert(it.messageStringRes)
            } else {
                alert(it.localizedMessage)
            }
        }
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

    override fun setFab(fabSetter: FabSetter?) {
        if (fabSetter != null) {
            fab.setOnClickListener { fabSetter.onClick.invoke() }

            fab.animate().apply {
                cancel()
                alpha(1f)
                fab.setImageResource(fabSetter.srcRes)
                duration = resources.getInteger(R.integer.duration_animation_short).toLong()
                (fab as View).show()
            }
        } else {
            fab.animate().apply {
                cancel()
                alpha(0f)
                duration = resources.getInteger(R.integer.duration_animation_short).toLong()
                withEndAction { (fab as View).hide() }
            }
        }
    }

    override fun liftNavBar() {
        if (bottom_navigation.translationY > 0f) {
            bottom_navigation.translationY = 0f
        }
    }

    override fun alert(stringRes: Int) {
        container.alert(stringRes)
    }

    override fun alert(string: String) {
        container.alert(string)
    }
}