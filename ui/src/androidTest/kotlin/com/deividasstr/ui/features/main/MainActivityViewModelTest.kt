package com.deividasstr.ui.features.main

import androidx.test.InstrumentationRegistry
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.repositories.PrefsRepo
import com.deividasstr.ui.utils.di.TestApplication
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityViewModelTest {

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var prefsRepo: PrefsRepo

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    /*@Inject
    lateinit var sharedPrefs: SweetsService*/

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
        app.appComponent.inject(this)
    }

    @Test
    fun activityStartNoSweets_downloadsAndSavesSweets() {

        given { sharedPrefs.sweetsUpdatedDate } willReturn { 0 }
        given { networkManager.networkAvailable } willReturn { true }

        activityRule.launchActivity(null)


    }
}
