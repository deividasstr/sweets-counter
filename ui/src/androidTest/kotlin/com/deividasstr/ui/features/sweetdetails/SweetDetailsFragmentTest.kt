package com.deividasstr.ui.features.sweetdetails

import android.os.Bundle
import androidx.navigation.NavController
import androidx.test.rule.ActivityTestRule
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.utils.AndroidTest
import com.deividasstr.ui.utils.TestActivity
import com.deividasstr.ui.utils.di.TestAppComponent
import com.deividasstr.utils.DataTestData
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

    @Mock
    lateinit var getSweetByIdUseCase: GetSweetByIdUseCase

    @Mock
    lateinit var addConsumedSweetUseCase: AddConsumedSweetUseCase

    @Mock
    lateinit var dateTimeHandler: DateTimeHandler

    private val fragment = TestSweetDetailsFragment().apply {
        val args = Bundle()
        args.putInt("sweetId", id)
        arguments = args
    }

    @Before
    fun setUp() {
    }

    @Test
    fun correctArgs() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        launchFragment()

        val sweetId = SweetDetailsFragmentArgs.fromBundle(fragment.arguments).sweetId
        assertEquals(UiTestData.UI_SWEET1.id.toInt(), sweetId)
    }

    @Test
    fun correctFieldsFromSweet() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }
        launchFragment()

        val sweet = UiTestData.UI_SWEET1

        R.id.sweet_name.containsText(sweet.name)

        R.id.cals_per_100_value.containsText(sweet.calsPer100.toInt().toString())

        R.id.serving_size_value.containsText(sweet.servingG.toInt().toString())

        R.id.rating_value.backgroundColor(R.color.rating_bad)

        R.id.protein_value.containsText(sweet.proteinG.toInt().toString())

        R.id.fat_value.containsText(sweet.fatG.toInt().toString())

        R.id.carbs_value.containsText(sweet.carbsG.toInt().toString())

        R.id.sugar_value.containsText(sweet.sugarG.toInt().toString())
    }

    @Test
    fun sweetRatingAverage_yellowColor() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL2.toSweet())
        }

        launchFragment()

        R.id.rating_value.backgroundColor(R.color.rating_average)
    }

    @Test
    fun sweetRatingGood_greenColor() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL3.toSweet())
        }

        launchFragment()

        R.id.rating_value.backgroundColor(R.color.rating_good)
    }

    @Test
    fun enterNoValue_displays0() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        launchFragment()

        R.id.consumed_sweet_amount.type("")

        R.id.total_cals_value.containsText("0")
    }

    @Test
    fun enterNoValue_pressComplete_showsError() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        launchFragment()

        R.id.consumed_sweet_amount.type("")

        R.id.consume_sweet_fab.click()

        activityRule.showsToastWithText(R.string.add_sweet_validation_fail)
    }

    @Test
    fun enterValue_showsCorrectCals() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        launchFragment()

        R.id.consumed_sweet_amount.type("2")

        R.id.total_cals_value.containsText("250")

        R.id.measure_units.click()

        R.id.total_cals_value.containsText("10")

        R.id.measure_servings.click()

        R.id.total_cals_value.containsText("250")
    }

    @Test
    fun enterValue_clickComplete_showsSavingError() {
        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        given { addConsumedSweetUseCase.execute(any()) } willReturn {
            Completable.error(StringResException(R.string.error_db_no_items))
        }

        launchFragment()

        R.id.consumed_sweet_amount.type("2")

        R.id.consume_sweet_fab.click()

        activityRule.showsToastWithText(R.string.error_db_no_items)
    }

    @Test
    fun enterValue_clickComplete_savesConsumedSweetAndNavigates() {
        val dateTimeMillis: Long = 123456789

        given { getSweetByIdUseCase.execute(any()) } willReturn {
            Single.just(DataTestData.TEST_SWEETMODEL.toSweet())
        }

        given { addConsumedSweetUseCase.execute(any()) } willReturn {
            Completable.complete()
        }

        given { dateTimeHandler.currentEpochSecs() } willReturn { dateTimeMillis }

        launchFragment()

        R.id.consumed_sweet_amount.type("2")

        R.id.consume_sweet_fab.click()

        val consumedSweet = ConsumedSweet(
            sweetId = DataTestData.TEST_SWEETMODEL.id,
            g = 250,
            date = dateTimeMillis)

        then(addConsumedSweetUseCase).should().execute(consumedSweet)
        then(fragment.navController).should().navigate(R.id.action_data)
    }

    private fun launchFragment() {
        activityRule.launchActivity(null)
        activityRule.activity.replaceFragment(fragment)
    }

    class TestSweetDetailsFragment : SweetDetailsFragment() {
        val navController: NavController = mock()
        override fun navController() = navController
    }
}