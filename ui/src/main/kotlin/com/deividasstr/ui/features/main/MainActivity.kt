package com.deividasstr.ui.features.main

import android.os.Bundle
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?) = 0
        /*savedInstanceState ?: supportFragmentManager.inTransaction { add(
            id.fragmentContainer, fragment()) }*/
}
