package com.deividasstr.ui.features.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.base.framework.alert
import com.deividasstr.ui.base.framework.viewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var networkManager: NetworkManager

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainActivityViewModel = viewModel(viewModelFactory) {
            when {
                networkManager.networkAvailable -> {
                    errorMessage.observe(this@MainActivity, errorObserver)
                    tryDownloadSweets()
                    tryDownloadFacts()
                }
                else -> alert(getString(R.string.error_network_unavailable))
            }
        }

        val navController = findNavController(R.id.fragment_container)
        setupWithNavController(
            findViewById<BottomNavigationView>(R.id.bottom_navigation),
            navController
        )
    }

    private val errorObserver = Observer<SingleEvent<Throwable>> {
        it?.getContentIfNotHandled()?.let {
            alert(getString(R.string.error_network_server))
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.fragment_container).navigateUp()
}
