package com.deividasstr.ui.utils

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.ui.utils.di.TestApplication
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AndroidTest {

    protected lateinit var app: TestApplication

    @Before
    fun before() {
        app = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
    }
}