package com.deividasstr.ui.features.facts

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.store.models.toFact
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestActivity
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willAnswer
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class FactsFragmentTest : AndroidTest() {

    @get:Rule
    val daggerRule = daggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(TestActivity::class.java, false, false)

    @Mock
    lateinit var randomFactUseCase: GetRandomFactUseCase

    private var fragment = FactsFragment()

    @Before
    fun setUp() {
        app.appComponent.inject(this)
    }

    @Test
    fun onStart_noFactsAvailable_showToast() {
        given { randomFactUseCase.execute(0) } willAnswer {
            Single.error(StringResException(R.string.error_no_facts_available))
        }

        activityRule.launchActivity(null)
        activityRule.activity.replaceFragment(FactsFragment())

        activityRule.showsToastWithText(R.string.error_no_facts_available)
    }

    @Test
    fun onStart_factDisplayed() {
        startFragmentWithFact()

        val factText = UiTestData.TEST_FACTMODEL_1.toFact().text
        onView(ViewMatchers.withId(R.id.fact)).check(matches(withText(factText)))
    }

    @Test
    fun pressNextFactButton_newRandomFactDisplayed() {
        startFragmentWithFact()

        given { randomFactUseCase.execute(UiTestData.TEST_FACTMODEL_1.id) } willAnswer {
            Single.just(UiTestData.TEST_FACTMODEL_2.toFact())
        }

        val factText = UiTestData.TEST_FACTMODEL_2.text

        onView(ViewMatchers.withId(R.id.next_fact_fab)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.fact)).check(matches(withText(factText)))
    }

    private fun startFragmentWithFact() {
        given { randomFactUseCase.execute(0) } willAnswer {
            Single.just(UiTestData.TEST_FACTMODEL_1.toFact())
        }

        activityRule.launchActivity(null)
        activityRule.activity.replaceFragment(fragment)
    }
}