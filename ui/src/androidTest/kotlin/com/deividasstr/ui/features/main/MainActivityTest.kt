package com.deividasstr.ui.features.main

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.FlakyTest
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.ui.R
import com.deividasstr.ui.features.consumedsweetdata.ConsumedSweetDataFragment
import com.deividasstr.ui.features.consumedsweethistory.ConsumedSweetHistoryFragment
import com.deividasstr.ui.features.facts.FactsFragment
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.di.TestAppComponent
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class MainActivityTest : AndroidTest() {

    @get:Rule
    val daggerRule: DaggerMockRule<TestAppComponent> = daggerMockRule()

    @Mock
    lateinit var networkManager: NetworkManager

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp() {
        app.appComponent.inject(this)
        given { networkManager.networkAvailable } willReturn { false }
        activityRule.launchActivity(null)
    }

    @Test
    fun pressOn1stBotNavItem_navigateToHistoryFragment() {
        onView(withId(R.id.action_history)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(ConsumedSweetHistoryFragment::class.java.simpleName, currentDestination?.label)
    }

    @Test
    fun pressOn2ndBotNavItem_navigateToDataFragment() {
        onView(withId(R.id.action_data)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(ConsumedSweetDataFragment::class.java.simpleName, currentDestination?.label)
    }

    @Test
    fun pressOn3rdBotNavItem_navigateToFactFragment() {
        onView(withId(R.id.action_fact)).perform(click())

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(FactsFragment::class.java.simpleName, currentDestination?.label)
    }

    @Test
    @FlakyTest // must be run with wifi off
    fun noInternet_alerts() {
        showsSnackWithText(R.string.error_network_unavailable)
    }
}
