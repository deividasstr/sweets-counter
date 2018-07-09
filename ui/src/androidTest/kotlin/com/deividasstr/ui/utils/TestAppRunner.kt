package com.deividasstr.ui.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestAppRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, "com.deividasstr.ui.utils.di.TestApplication", context)
    }
}