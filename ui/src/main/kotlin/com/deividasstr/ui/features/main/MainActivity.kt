package com.deividasstr.ui.features.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.alert
import com.deividasstr.ui.base.framework.viewModel

class MainActivity : BaseActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = viewModel(viewModelFactory) {
            tryDownloadSweets()
            errorMessage.observe(this@MainActivity, Observer {
                it?.getContentIfNotHandled()?.let {
                    alert(it.message!!)
                }
            })
        }

        val navController = findNavController(R.id.fragment_container)
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.fragment_container).navigateUp()

}
