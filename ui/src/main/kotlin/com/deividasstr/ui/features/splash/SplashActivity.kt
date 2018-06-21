package com.deividasstr.ui.features.splash

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import com.deividasstr.ui.base.framework.BaseActivity
import com.deividasstr.ui.base.framework.alert
import com.deividasstr.ui.base.framework.viewModel
import com.deividasstr.ui.features.main.MainActivity
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private lateinit var splashActivityViewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityViewModel = viewModel(viewModelFactory) {
            tryDownloadSweets()
            errorMessage.observe(this@SplashActivity, Observer {
                it?.getContentIfNotHandled()?.let {
                    Timber.e("errr ${it.message}")
                    alert(it.message!!)
                }
            })
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}