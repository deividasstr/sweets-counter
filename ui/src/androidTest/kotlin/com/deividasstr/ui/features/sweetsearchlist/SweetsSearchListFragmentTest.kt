package com.deividasstr.ui.features.sweetsearchlist

import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.ui.R
import com.deividasstr.ui.features.main.MainActivity
import com.deividasstr.ui.features.sweetdetails.SweetDetailsFragment
import com.deividasstr.ui.features.sweetdetails.SweetDetailsFragmentArgs
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.di.TestAppComponent
import com.deividasstr.ui.utils.di.TestApplication
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import it.cosenonjaviste.daggermock.DaggerMockRule
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import javax.inject.Inject

class SweetsSearchListFragmentTest : AndroidTest() {

    @get:Rule
    val daggerRule: DaggerMockRule<TestAppComponent> = daggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Inject
    lateinit var sweetsRepo: SweetsDao

    @Mock
    lateinit var networkManager: NetworkManager

    @Before
    fun setup() {
        app.appComponent.inject(this)

        given { networkManager.networkAvailable } willReturn { false }

        sweetsRepo.addSweets(UiTestData.TEST_LIST_SWEETMODELS).blockingAwait()

        activityRule.launchActivity(null)
        activityRule.activity.findNavController(R.id.fragment_container)
            .navigate(R.id.action_consumedSweetHistoryFragment_to_sweetsSearchListFragment)
    }

    @Test
    fun shouldTestApplicationName() {
        assertEquals(
            TestApplication::class.java.name,
            app.javaClass.name
        )
    }

    @Test
    fun lists2Items() {
        assertEquals(
            UiTestData.TEST_LIST_SWEETMODELS.size,
            countRecyclerViewItems(R.id.sweets_list)
        )
    }

    @Test
    fun enterSweetName_filters1Val() {
        onView(withId(androidx.appcompat.R.id.search_src_text))
            .perform(typeText(UiTestData.TEST_SWEETMODEL.name))
        assertEquals(1, countRecyclerViewItems(R.id.sweets_list))
    }

    @Test
    fun clickRecyclerViewItem_goToSweetDetailFragmentWithCorrectSweetId() {
        onView(withId(R.id.sweets_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<SweetsSearchAdapter.SweetViewHolder>(
                    0,
                    click()
                )
            )

        val currentDestination =
            activityRule.activity.findNavController(R.id.fragment_container).currentDestination

        assertEquals(SweetDetailsFragment::class.java.simpleName, currentDestination.label)

        val arguments =
            activityRule.activity.fragment_container.childFragmentManager.fragments.last().arguments
        val sweetId = SweetDetailsFragmentArgs.fromBundle(arguments).sweetId.toLong()

        assertEquals(UiTestData.TEST_SWEETMODEL.id, sweetId)
    }
}