package com.deividasstr.ui.features.consumedsweetdata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.enums.MeasurementUnit
import com.deividasstr.domain.enums.Periods
import com.deividasstr.domain.utils.DateRange
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.willReturn
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import kotlin.math.roundToLong

class ConsumedDataPeriodViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedDataPeriodViewModel

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @Mock
    private var dateTimeHandler = DateTimeHandler()

    @Mock
    private lateinit var sharedPrefs: SharedPrefs

    // Sweets on days - 2 on current day, 1 day before yesterday, 1 last week, 1 last month, 1 last year
    @Before
    fun setUp() {
        given { dateTimeHandler.currentLocalDate() } willReturn {
            TestData.LOCAL_DATE_TIME.toLocalDate()
        }

        given { sharedPrefs.defaultMeasurementUnit } willReturn {
            MeasurementUnit.GRAM
        }

        viewModel = ConsumedDataPeriodViewModel(dateTimeHandler, sharedPrefs)
    }

    // Consumed 1 on monday and 2 wednesday
    @Test
    fun defaultInit() {
        defaultVMInit()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val cals = calsDay1 + calsDay3

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1.name,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(UiTestData.UI_SWEET2.name, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET2.g)
            )

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    // Consumed in 3 days
    @Test
    fun changePeriodToMonth() {
        defaultVMInit()

        val calsDayLastWeek =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100)
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val cals = calsDay1 + calsDay3 + calsDayLastWeek

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.MONTH, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1.name,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(
                    UiTestData.UI_SWEET2.name,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(
                    SweetRating.AVERAGE,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val days =
            TestData.LOCAL_DATE_TIME.month.length(TestData.LOCAL_DATE_TIME.toLocalDate().isLeapYear)
        val array =
            LongArray(days) { 0 }
        array[4] = calsDayLastWeek
        array[5] = calsDay1
        array[7] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.MONTH)

        viewModel.setPeriod(Periods.MONTH)
        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    // Consumed in 2 months
    @Test
    fun changePeriodToYear() {
        defaultVMInit()

        val calsDayLastMonth =
            (TestData.TEST_CONSUMED_SWEET5.g * TestData.TEST_SWEET.calsPer100 / 100)
        val calsDayLastWeek =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100)
        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val cals = calsDay1 + calsDay3 + calsDayLastWeek + calsDayLastMonth

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.YEAR, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1.name,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g + UiTestData.UI_CONSUMED_SWEET5.g),
                PopularitySweetUi(
                    UiTestData.UI_SWEET2.name,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g + UiTestData.UI_CONSUMED_SWEET5.g),
                Pair(
                    SweetRating.AVERAGE,
                    UiTestData.UI_CONSUMED_SWEET2.g + UiTestData.UI_CONSUMED_SWEET4.g))

        val array =
            LongArray(12) { 0 }
        array[6] = calsDayLastMonth
        array[7] = calsDay1 + calsDay3 + calsDayLastWeek
        val consumedData = ConsumedBarData(array, Periods.YEAR)

        viewModel.setPeriod(Periods.YEAR)
        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    // Next week - no consumed
    @Test
    fun nextRange() {
        viewModel.pos++

        defaultVMInit()

        val cals = 0L

        val weight = 0L

        val range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange()

        val sweetsPopularity = emptyList<PopularitySweetUi>()

        val sweetsRating: Map<SweetRating, Long> = emptyMap()

        val array = LongArray(7) { 0 }
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    // Last week - 1 consumed on sunday
    @Test
    fun previousRange() {
        viewModel.pos--
        defaultVMInit()

        val cals =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange(-1)

        val sweetsPopularity = listOf(
            PopularitySweetUi(UiTestData.UI_SWEET2.name, UiTestData.UI_CONSUMED_SWEET4.g))

        val sweetsRating =
            mapOf(Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET4.g))

        val array = LongArray(7) { 0 }
        array[6] = cals
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        viewModel.pos = -1

        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    @Test
    fun resetPeriod() {
        defaultVMInit()

        viewModel.barClicked(3)

        viewModel.resetPeriod()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val cals = calsDay1 + calsDay3

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(Periods.WEEK, dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(
                    UiTestData.UI_SWEET1.name,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                PopularitySweetUi(UiTestData.UI_SWEET2.name, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(
                    SweetRating.BAD,
                    UiTestData.UI_CONSUMED_SWEET1.g + UiTestData.UI_CONSUMED_SWEET3.g),
                Pair(SweetRating.AVERAGE, UiTestData.UI_CONSUMED_SWEET2.g))

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = calsDay3
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    // Wednesday with 2 consumed
    @Test
    fun timeUnitSelected() {
        defaultVMInit()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val cals = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val weight = (cals / ConsumedDataPeriodViewModel.CALS_PER_G).roundToLong()

        val range = DateRange(
            Periods.WEEK,
            dateTimeHandler)

        val sweetsPopularity =
            listOf(
                PopularitySweetUi(UiTestData.UI_SWEET1.name, UiTestData.UI_CONSUMED_SWEET1.g),
                PopularitySweetUi(UiTestData.UI_SWEET2.name, UiTestData.UI_CONSUMED_SWEET2.g))

        val sweetsRating =
            mapOf(
                Pair(SweetRating.BAD, 100L),
                Pair(SweetRating.AVERAGE, 65L)
            )

        val array = LongArray(7) { 0 }
        array[0] = calsDay1
        array[2] = cals
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        viewModel.barClicked(3)

        testVals(cals, weight, range, sweetsPopularity, sweetsRating, consumedData)
    }

    private fun defaultVMInit() {
        viewModel.setSweets(Pair(TestData.TEST_LIST_CONSUMED_SWEETS3.map { ConsumedSweetUi(it) },
            TestData.TEST_LIST_SWEETS.map { SweetUi(it) }))
        viewModel.setPeriod(Periods.WEEK)
    }

    private fun testVals(
        cals: Long,
        weight: Long,
        range: DateRange,
        sweetsPopularity: List<PopularitySweetUi>,
        sweetsRating: Map<SweetRating, Long>,
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
        then(dateTimeHandler).should().formattedDateRange(range)

        val sweetsPopularityObserver: Observer<List<PopularitySweetUi>> = mock()
        viewModel.sweetsPopularityData.observeForever(sweetsPopularityObserver)
        then(sweetsPopularityObserver).should().onChanged(sweetsPopularity)

        val sweetsRatingObserver: Observer<Map<SweetRating, Long>> = mock()
        viewModel.sweetsRatingData.observeForever(sweetsRatingObserver)
        then(sweetsRatingObserver).should().onChanged(sweetsRating)

        val consumedObserver: Observer<ConsumedBarData> = mock()
        viewModel.consumedBarData.observeForever(consumedObserver)
        then(consumedObserver).should().onChanged(consumedData)
    }
}