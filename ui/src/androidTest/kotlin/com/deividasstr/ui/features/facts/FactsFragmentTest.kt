package com.deividasstr.ui.features.facts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.di.modules.NetworkModule
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.data.store.models.toFact
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestActivity
import com.deividasstr.ui.utils.di.TestAppComponent
import com.deividasstr.ui.utils.di.TestUseCasesModule
import com.deividasstr.utils.DataTestData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runners.MethodSorters
import org.mockito.Mock

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FactsFragmentTest : AndroidTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var daggerRule: DaggerMockRule<TestAppComponent> =
        DaggerMockRule(
            TestAppComponent::class.java,
            TestUseCasesModule(),
            NetworkModule("https://hello.world.com/api/"),
            SharedPrefsModule(),
            DbModule(), app
        )
            .set { component ->
                app.setComponent(component)
            }

    @get:Rule
    val activityRule = ActivityTestRule(TestActivity::class.java, true, false)

    @Mock lateinit var randomFactUseCase: GetRandomFactUseCase

    @Before
    fun setUp() {
        app.appComponent.inject(this)

        given { randomFactUseCase.execute(any()) } willReturn { Single.just(DataTestData.TEST_FACTMODEL_1.toFact()) }

        activityRule.launchActivity(null)
        activityRule.activity.setFragment(FactsFragment())
    }

    @Test
    fun a_onStart_factDisplayed() {
        val factText = DataTestData.TEST_FACTMODEL_1.toFact().text
        onView(ViewMatchers.withId(R.id.fact)).check(matches(withText(factText)))
    }

    @Test
    fun pressNextFactButton_newRandomFactDisplayed() {
        given { randomFactUseCase.execute(any()) } willReturn { Single.just(DataTestData.TEST_FACTMODEL_2.toFact()) }
        val factText = DataTestData.TEST_FACTMODEL_2.text

        onView(ViewMatchers.withId(R.id.next_fact_fab)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.fact)).check(matches(withText(factText)))
    }
}