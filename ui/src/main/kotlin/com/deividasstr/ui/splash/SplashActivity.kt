package com.deividasstr.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.deividasstr.ui.base.MainActivity

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}