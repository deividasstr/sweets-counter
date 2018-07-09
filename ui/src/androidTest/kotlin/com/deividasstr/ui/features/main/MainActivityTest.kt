package com.deividasstr.ui.features.main

import androidx.navigation.findNavController
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.ui.R
import com.deividasstr.ui.features.consumedsweetdata.ConsumedSweetDataFragment
import com.deividasstr.ui.features.facts.FactsFragment
import com.deividasstr.ui.features.sweethistory.ConsumedSweetHistoryFragment
import com.deividasstr.ui.utils.di.TestApplication
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    @Inject
    lateinit var networkManager: NetworkManager

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
    }

    @Test
    fun pressOn1stBotNavItem_navigateToHistoryFragment() {
        activityRule.launchActivity(null)
        onView(withId(R.id.action_history)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(ConsumedSweetHistoryFragment::class.java.simpleName, currentDestination.label)
    }

    @Test
    fun pressOn2ndBotNavItem_navigateToHistoryFragment() {
        activityRule.launchActivity(null)
        onView(withId(R.id.action_data)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(ConsumedSweetDataFragment::class.java.simpleName, currentDestination.label)
    }

    @Test
    fun pressOn3rdBotNavItem_navigateToHistoryFragment() {
        activityRule.launchActivity(null)
        onView(withId(R.id.action_fact)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(FactsFragment::class.java.simpleName, currentDestination.label)
    }

    @Test
    fun noInternet_showsToast() {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as TestApplication
        app.appComponent.inject(this)

        given { networkManager.networkAvailable } willReturn { false }
        given { sharedPrefs.sweetsUpdatedDate } willReturn { 3 }

        activityRule.launchActivity(null)

        //given { networkManager.networkAvailable } willReturn { false }

        onView(
            withText(R.string.error_network_unavailable)
        )
            .inRoot(withDecorView(not(activityRule.activity.window.decorView)))
            .check(matches(isDisplayed()))
    }
}