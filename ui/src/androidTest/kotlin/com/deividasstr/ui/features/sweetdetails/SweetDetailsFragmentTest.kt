package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestActivity
import com.deividasstr.ui.utils.di.TestAppComponent
import com.deividasstr.utils.AsyncTaskSchedulerRule
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Completable
import io.reactivex.Single
import it.cosenonjaviste.daggermock.DaggerMockRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

class SweetDetailsFragmentTest : AndroidTest() {

    @get:Rule
    val daggerRule: DaggerMockRule<TestAppComponent> = daggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(TestActivity::class.java, true, false)

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()
    @get:Rule
    val instantRx = AsyncTaskSchedulerRule()

    @Mock
    lateinit var getSweetByIdUseCase: GetSweetByIdUseCase

    @Mock
    lateinit var addConsumedSweetUseCase: AddConsumedSweetUseCase

    @Mock
    lateinit var dateTimeHandler: DateTimeHandler

    @Before
    fun setUp() {
        given { getSweetByIdUseCase.execute(UiTestData.TEST_SWEETMODEL.id) } willReturn {
            Single.just(UiTestData.TEST_SWEETMODEL.toSweet())
        }

        given { getSweetByIdUseCase.execute(UiTestData.TEST_SWEETMODEL2.id) } willReturn {
            Single.just(UiTestData.TEST_SWEETMODEL2.toSweet())
        }

        given { getSweetByIdUseCase.execute(UiTestData.TEST_SWEETMODEL3.id) } willReturn {
            Single.just(UiTestData.TEST_SWEETMODEL3.toSweet())
        }
    }

    @Test
    fun correctArgs() {
        val initId = 2
        val fragment = fragment(initId)

        launchFragment(fragment)

        val sweetId = SweetDetailsFragmentArgs.fromBundle(fragment.arguments).sweetId
        assertEquals(initId, sweetId)
    }

    @Test
    fun correctFieldsFromSweet() {
        val fragment = fragment(UiTestData.UI_SWEET1.id.toInt())
        launchFragment(fragment)

        val sweet = UiTestData.UI_SWEET1

        R.id.sweet_name.containsText(sweet.name)

        R.id.cals_per_100_value.containsText(sweet.calsPer100.toInt().toString())

        R.id.rating_value.backgroundColor(R.color.rating_bad)

        R.id.protein_value.containsText(sweet.proteinG.toInt().toString())

        R.id.fat_value.containsText(sweet.fatG.toInt().toString())

        R.id.carbs_value.containsText(sweet.carbsG.toInt().toString())

        R.id.sugar_value.containsText(sweet.sugarG.toInt().toString())
    }

    @Test
    fun sweetRatingAverage_yellowColor() {
        val fragment = fragment(UiTestData.UI_SWEET2.id.toInt())
        launchFragment(fragment)

        R.id.rating_value.backgroundColor(R.color.rating_average)
    }

    @Test
    fun sweetRatingGood_greenColor() {
        val fragment = fragment(UiTestData.UI_SWEET3.id.toInt())
        launchFragment(fragment)

        R.id.rating_value.backgroundColor(R.color.rating_good)
    }

    @Test
    fun enterNoValue_displays0() {
        val fragment = fragment(UiTestData.UI_SWEET1.id.toInt())
        launchFragment(fragment)

        R.id.consumed_sweet_amount.type("")

        R.id.total_cals_value.containsText("0")
    }

    @Test
    fun enterNoValue_pressComplete_showsError() {
        val fragment = fragment(UiTestData.UI_SWEET1.id.toInt())
        launchFragment(fragment)

        R.id.consumed_sweet_amount.type("")

        R.id.consume_sweet_fab.click()

        activityRule.showsToastWithText(R.string.add_sweet_validation_fail)
    }

    @Test
    fun enterValue_showsCorrectCals() {
        val fragment = fragment(UiTestData.UI_SWEET1.id.toInt())
        launchFragment(fragment)

        R.id.consumed_sweet_amount.type("2")

        R.id.total_cals_value.containsText("10")

        R.id.measure_ounces.click()

        R.id.total_cals_value.containsText("283.5")

        R.id.measure_grams.click()

        R.id.total_cals_value.containsText("10")
    }

    @Test
    fun enterValue_clickComplete_savesConsumedSweetAndNavigates() {
        val dateTimeMillis: Long = 123456789

        given { addConsumedSweetUseCase.execute(any()) } willReturn {
            Completable.complete()
        }

        given { dateTimeHandler.currentEpochSecs() } willReturn { dateTimeMillis }

        val fragment = fragment(UiTestData.UI_SWEET1.id.toInt())
        launchFragment(fragment)

        R.id.consumed_sweet_amount.type("2")

        R.id.consume_sweet_fab.click()

        val consumedSweet = ConsumedSweet(
            sweetId = UiTestData.TEST_SWEETMODEL.id,
            g = 50,
            date = dateTimeMillis)

        then(addConsumedSweetUseCase).should().execute(consumedSweet)
        then(fragment.navController).should()
            .navigate(R.id.action_sweetDetailsFragment_to_consumedSweetHistoryFragment)
    }

    private fun fragment(id: Int): TestSweetDetailsFragment {
        return TestSweetDetailsFragment().apply {
            val args = Bundle()
            args.putInt("sweetId", id)
            arguments = args
        }
    }

    private fun launchFragment(fragment: SweetDetailsFragment) {
        activityRule.launchActivity(null)
        activityRule.activity.replaceFragment(fragment)
    }

    class TestSweetDetailsFragment : SweetDetailsFragment() {
        val navController: NavController = mock()
        override fun navController() = navController
    }
}