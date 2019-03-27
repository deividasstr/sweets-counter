package com.deividasstr.ui.features.consumedsweetdata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.common.TestData
import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.LowerPeriodModel
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.UpperPeriodModel
import com.deividasstr.ui.features.sweetdetails.SweetRating
import com.deividasstr.utils.UiTestData
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.willReturn
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Spy
import kotlin.math.roundToLong

class ConsumedDataPeriodViewModelTest : UnitTest() {

    private lateinit var viewModel: ConsumedPeriodViewModel
    private val realDateTimeHandler = DateTimeHandler()

    @get:Rule
    val instantLiveData = InstantTaskExecutorRule()

    @Spy
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

        viewModel = ConsumedPeriodViewModel(dateTimeHandler, sharedPrefs, ConsumedDataGenerator)
    }

    // Consumed 1 on monday and 2 wednesday
    @Test
    fun defaultInit() {
        defaultVMInit()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)
        val unitsConsumed =
            TestData.TEST_CONSUMED_SWEET.g + TestData.TEST_CONSUMED_SWEET3.g + TestData.TEST_CONSUMED_SWEET2.g

        val cals = calsDay1 + calsDay3

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

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

        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
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
        val unitsConsumed =
            TestData.TEST_CONSUMED_SWEET.g + TestData.TEST_CONSUMED_SWEET2.g + TestData.TEST_CONSUMED_SWEET3.g + TestData.TEST_CONSUMED_SWEET4.g

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

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
        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
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

        val unitsConsumed =
            TestData.TEST_CONSUMED_SWEET.g + TestData.TEST_CONSUMED_SWEET2.g + TestData.TEST_CONSUMED_SWEET3.g + TestData.TEST_CONSUMED_SWEET4.g + TestData.TEST_CONSUMED_SWEET5.g

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

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
        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
    }

    // Next week - no consumed
    @Test
    fun nextRange() {
        viewModel.pos++

        defaultVMInit()

        val cals = 0L

        val weight = 0L

        val unitsConsumed = 0L

        val range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange()

        val sweetsPopularity = null

        val sweetsRating = null

        val array = LongArray(7) { 0 }
        val consumedData = ConsumedBarData(array, Periods.WEEK)

        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
    }

    // Last week - 1 consumed on sunday
    @Test
    fun previousRange() {
        viewModel.pos--
        defaultVMInit()

        val cals =
            (TestData.TEST_CONSUMED_SWEET4.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

        val unitsConsumed = TestData.TEST_CONSUMED_SWEET4.g

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

        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
    }

    @Test
    fun resetPeriod() {
        defaultVMInit()

        viewModel.toggleSubPeriod(3)

        viewModel.resetPeriod()

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val calsDay3 = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val cals = calsDay1 + calsDay3

        val unitsConsumed =
            TestData.TEST_CONSUMED_SWEET.g + TestData.TEST_CONSUMED_SWEET2.g + TestData.TEST_CONSUMED_SWEET3.g

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

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

        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)
        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            null,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
    }

    // Wednesday with 2 consumed
    @Test
    fun timeUnitSelected() {
        defaultVMInit()
        val selectedPeriod = 3

        val calsDay1 =
            (TestData.TEST_CONSUMED_SWEET3.g * TestData.TEST_SWEET.calsPer100 / 100)

        val cals = (TestData.TEST_CONSUMED_SWEET.g * TestData.TEST_SWEET.calsPer100 / 100 +
            TestData.TEST_CONSUMED_SWEET2.g * TestData.TEST_SWEET2.calsPer100 / 100)

        val weight = (cals / ConsumedDataGenerator.CALS_PER_G).roundToLong()

        val unitsConsumed = TestData.TEST_CONSUMED_SWEET.g + TestData.TEST_CONSUMED_SWEET2.g
        +TestData.TEST_CONSUMED_SWEET3.g

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

        viewModel.toggleSubPeriod(selectedPeriod)

        val clickRange = DateRange(Periods.DAY, dateTimeHandler, range.startDate)
        clickRange.advanceRange((selectedPeriod - 1).toLong())

        val lowerPeriodDateRangeText = realDateTimeHandler.formattedDateShort(clickRange.startDate)

        val upperPeriodModel =
            UpperPeriodModel(realDateTimeHandler.formattedDateRange(range), consumedData)

        val lowerPeriodModel = LowerPeriodModel(
            cals,
            weight,
            unitsConsumed,
            sweetsPopularity,
            sweetsRating,
            lowerPeriodDateRangeText,
            R.string.unit_grams)

        testVals(upperPeriodModel, lowerPeriodModel)
    }

    private fun defaultVMInit() {
        viewModel.setSweets(TestData.TEST_LIST_CONSUMED_SWEETS3.map { ConsumedSweetUi(it) })
        viewModel.setPeriod(Periods.WEEK)
    }

    private fun testVals(upperPeriodModel: UpperPeriodModel, lowerPeriodModel: LowerPeriodModel) {
        val upperObserver: Observer<UpperPeriodModel> = mock()
        viewModel.upperPeriodModel.observeForever(upperObserver)
        then(upperObserver).should().onChanged(upperPeriodModel)

        val lowerObserver: Observer<LowerPeriodModel> = mock()
        viewModel.lowerPeriodModel.observeForever(lowerObserver)
        then(lowerObserver).should().onChanged(lowerPeriodModel)
    }
}