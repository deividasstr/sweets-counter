package com.deividasstr.ui.utils

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.ui.utils.di.TestApplication
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class AndroidTest {

    val app: TestApplication
        get() = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
}