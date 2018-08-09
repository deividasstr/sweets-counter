package com.deividasstr.ui.features.consumedsweetdata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.domain.utils.DateRange
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.domain.utils.Periods
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.deividasstr.utils.AsyncTaskSchedulerRule
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import kotlin.math.roundToLong

class ConsumedSweetDataViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedSweetDataViewModel

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @get:Rule
    val instantRx = AsyncTaskSchedulerRule()

    @Mock
    private val dateTimeHandler = DateTimeHandler()

    @Mock
    lateinit var getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase

    @Mock
    lateinit var getSweetsByIdsUseCase: GetSweetsByIdsUseCase

    // Sweets on days - 2 on current day, 1 day before yesterday, 1 last week, 1 last month, 1 last year
    @Before
    fun setUp() {
        given { dateTimeHandler.currentLocalDate() } willReturn {
            TestData.LOCAL_DATE_TIME.toLocalDate()
        }
        given { dateTimeHandler.currentEpochSecs() } willReturn { TestData.CURRENT_EPOCH_SECS }
        given { getAllConsumedSweetsUseCase.execute() } willReturn {
            Single.just(TestData.TEST_LIST_CONSUMED_SWEETS3)
        }
        given { getSweetsByIdsUseCase.execute(any()) } willReturn {
            Single.just(TestData.TEST_LIST_SWEETS)
        }

        viewModel = ConsumedSweetDataViewModel(
            getAllConsumedSweetsUseCase,
            getSweetsByIdsUseCase,
            dateTimeHandler)
    }

    // Consumed 1 on monday and 2 wednesday
    @Test
    fun defaultInit() {
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val cals = calsDay1 + calsDay3

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(UiTestData.UI_SWEET2, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET2.g),
                Pair(SweetRating.GOOD, 0)
            )

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        testVals(cals, weight, range, 1, sweetsPopularity, sweetsRating, consumedData)
    }

    // Consumed in 3 days
    @Test
    fun changePeriodToMonth() {
        val calsDayLastWeek =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val cals = calsDay1 + calsDay3 + calsDayLastWeek

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.MONTH, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(
                    UiTestData.UI_SWEET2,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(
                    SweetRating.AVERAGE,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g),
                Pair(SweetRating.GOOD, 0)
            )

        val array =
            LongArray(TestData.LOCAL_DATE_TIME.month.length(TestData.LOCAL_DATE_TIME.toLocalDate().isLeapYear)) { 0 }
        array[4] = calsDayLastWeek
        array[5] = calsDay1
        array[7] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.MONTH)

        viewModel.changePeriod(1)
        testVals(cals, weight, range, 1, sweetsPopularity, sweetsRating, consumedData)
    }

    // Consumed in 2 months
    @Test
    fun changePeriodToYear() {
        val calsDayLastMonth =
            (TestData.TEST_CONSUMED_SWEET5.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()
        val calsDayLastWeek =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val cals = calsDay1 + calsDay3 + calsDayLastWeek + calsDayLastMonth

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.YEAR, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g + UiTestData.UI_CONSUMED_SWEET5.g),
                PopularitySweetUi(
                    UiTestData.UI_SWEET2,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g + UiTestData.UI_CONSUMED_SWEET5.g),
                Pair(
                    SweetRating.AVERAGE,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g),
                Pair(SweetRating.GOOD, 0)
            )

        val array =
            LongArray(12) { 0 }
        array[6] = calsDayLastMonth
        array[7] = calsDay1 + calsDay3 + calsDayLastWeek
        val consumedData = ConsumedBarData(array, Periods.YEAR)

        viewModel.changePeriod(2)
        testVals(cals, weight, range, 1, sweetsPopularity, sweetsRating, consumedData)
    }

    // Next week - no consumed
    @Test
    fun nextRange() {
        val cals = 0L

        val weight = 0L

        val range = DateRange(Periods.WEEK, dateTimeHandler)
        range.nextRange()

        val sweetsPopularity = emptyList<PopularitySweetUi>()

        val sweetsRating =
            mapOf(
                Pair(SweetRating.BAD, 0),
                Pair(SweetRating.AVERAGE, 0),
                Pair(SweetRating.GOOD, 0)
            )

        val array = LongArray(7) { 0 }
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        viewModel.nextRange()

        testVals(cals, weight, range, 2, sweetsPopularity, sweetsRating, consumedData)
    }

    // Last week - 1 consumed on sunday
    @Test
    fun previousRange() {
        val cals =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)
        range.previousRange()

        val sweetsPopularity = listOf(
            PopularitySweetUi(UiTestData.UI_SWEET2, UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(
                Pair(SweetRating.BAD, 0),
                Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET4.g),
                Pair(SweetRating.GOOD, 0)
            )

        val array = LongArray(7) { 0 }
        array[6] = cals
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        viewModel.previousRange()

        testVals(cals, weight, range, 2, sweetsPopularity, sweetsRating, consumedData)
    }

    @Test
    fun resetPeriod() {
        timeUnitSelected()
        viewModel.resetPeriod()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val cals = calsDay1 + calsDay3

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(UiTestData.UI_SWEET2, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET2.g),
                Pair(SweetRating.GOOD, 0)
            )

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        testVals(cals, weight, range, 2, sweetsPopularity, sweetsRating, consumedData)
    }

    // Wednesday with 2 consumed
    @Test
    fun timeUnitSelected() {
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100).roundToLong()

        val cals = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100).roundToLong()

        val weight = (cals / ConsumedSweetDataViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(
            Periods.WEEK,
            dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(UiTestData.UI_SWEET1, UiTestData.UI_CONSUMED_SWEET1.g),
                PopularitySweetUi(UiTestData.UI_SWEET2, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(SweetRating.BAD, 100),
                Pair(SweetRating.AVERAGE, 65),
                Pair(SweetRating.GOOD, 0)
            )

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = cals
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        viewModel.timeUnitSelected(3)

        testVals(cals, weight, range, 1, sweetsPopularity, sweetsRating, consumedData)
    }

    fun testVals(
        cals: Long,
        weight: Long,
        range: DateRange,
        rangeInvokeTimes: Int,
        sweetsPopularity: List<PopularitySweetUi>,
        sweetsRating: Map<SweetRating, Int>,
        consumedData: ConsumedBarData
    ) {

        val calObserver: Observer<Long> = mock()
        viewModel.cals.observeForever(calObserver)
        then(calObserver).should().onChanged(cals)

        val weightObserver: Observer<Long> = mock()
        viewModel.weight.observeForever(weightObserver)
        then(weightObserver).should().onChanged(weight)

        val textRangeObserver: Observer<String> = mock()
        viewModel.dateRangeText.observeForever(textRangeObserver)
        then(dateTimeHandler).should(times(rangeInvokeTimes)).formattedDateRange(range)

        val sweetsPopularityObserver: Observer<List<PopularitySweetUi>> = mock()
        viewModel.sweetsPopularityData.observeForever(sweetsPopularityObserver)
        then(sweetsPopularityObserver).should().onChanged(sweetsPopularity)

        val sweetsRatingObserver: Observer<Map<SweetRating, Int>> = mock()
        viewModel.sweetsRatingData.observeForever(sweetsRatingObserver)
        then(sweetsRatingObserver).should().onChanged(sweetsRating)

        val consumedObserver: Observer<ConsumedBarData> = mock()
        viewModel.consumedBarData.observeForever(consumedObserver)
        then(consumedObserver).should().onChanged(consumedData)
    }
}