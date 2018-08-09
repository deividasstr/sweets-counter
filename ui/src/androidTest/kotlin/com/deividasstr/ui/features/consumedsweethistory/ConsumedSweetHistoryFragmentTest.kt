package com.deividasstr.ui.features.consumedsweethistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.rule.ActivityTestRule
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import java.math.BigDecimal

class ConsumedSweetHistoryFragmentTest : AndroidTest() {

    @get:Rule
    val daggerRule = daggerMockRule()

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @get:Rule
    val activityRule = ActivityTestRule(TestActivity::class.java, false, false)

    @Mock
    lateinit var getSweetsByIdsUseCase: GetSweetsByIdsUseCase
    @Mock
    lateinit var getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase

    private lateinit var fragment: ConsumedSweetHistoryFragment

    private val dateTimeHandler = DateTimeHandler()

    @Before
    fun setUp() {
        app.appComponent.inject(this)
        fragment = ConsumedSweetHistoryFragment()
    }

    @Test
    fun recyclerViewItems() {
        val sweets = TestData.TEST_LIST_SWEETS
        val consumedSweets = TestData.TEST_LIST_CONSUMED_SWEETS
        given { getSweetsByIdsUseCase.execute(any()) } willReturn { Single.just(sweets) }
        given { getAllConsumedSweetsUseCase.execute() } willReturn { Single.just(consumedSweets) }

        startFragment()

        //2 headers, 2 containers for items = 4 views
        assertEquals(4, countRecyclerViewItems(R.id.consumed_sweet_recycler))

        // Sticky headers lib makes header views not as containing text
        /*R.id.consumed_sweet_recycler
            .nthItemHasText(0, dateTimeHandler.formattedDateFull(TestData.TEST_CONSUMED_SWEET.date))*/

        R.id.consumed_sweet_recycler.nthItemHasText(1, TestData.TEST_SWEET.name)
        R.id.consumed_sweet_recycler
            .nthItemHasText(1, dateTimeHandler.formattedTime(TestData.TEST_CONSUMED_SWEET.date))
        R.id.consumed_sweet_recycler.nthItemHasText(1, TestData.TEST_CONSUMED_SWEET.g.toString())
        val cals = BigDecimal(TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100)
        R.id.consumed_sweet_recycler.nthItemHasText(1, cals.toString())
    }

    private fun startFragment() {
        activityRule.launchActivity(null)
        activityRule.activity.replaceFragment(fragment)
    }
}