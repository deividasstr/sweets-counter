package com.deividasstr.ui.utils

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Nullable
import androidx.annotation.RestrictTo
import androidx.fragment.app.Fragment
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.FabSetter
import com.deividasstr.ui.base.framework.base.BaseActivity
import com.deividasstr.ui.base.framework.extensions.hide
import com.deividasstr.ui.base.framework.extensions.show
import kotlinx.android.synthetic.main.activity_main.*

@RestrictTo(RestrictTo.Scope.TESTS)
class TestActivity : BaseActivity() {

    override fun liftNavBar() {
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

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val content = FrameLayout(this)
        content.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        content.id = R.id.fragment_container

        setContentView(R.layout.activity_main)
        container.removeViewAt(0)
        container.addView(content)
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, "TEST")
            .commit()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }

    fun currentFragment(): Fragment? {
        return supportFragmentManager.fragments.last()
    }
}
