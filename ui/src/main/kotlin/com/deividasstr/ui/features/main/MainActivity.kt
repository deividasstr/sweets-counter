package com.deividasstr.ui.features.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.fragment_container)
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(
            navController
        )
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.fragment_container).navigateUp()
}
