package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.enums.MeasurementUnit
import com.deividasstr.domain.enums.Periods
import com.deividasstr.domain.utils.DateRange
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.calsFromConsumed
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.getConsumedInRange
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.sweetPopularityByG
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.sweetRatings
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.weightGFromCals
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts
import com.deividasstr.ui.features.sweetdetails.SweetRating
import javax.inject.Inject

class ConsumedPeriodViewModel
@Inject constructor(
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs,
    private val dataGenerator: ConsumedDataGenerator
) : BaseViewModel() {

    private lateinit var consumedSweets: List<ConsumedSweetUi>
    private lateinit var calsPerTimeUnits: LongArray
    private lateinit var consumedInRange: List<ConsumedSweetUi>

    private var clickRange: DateRange? = null

    // Observed by a view
    val cals = MutableLiveData<Long>().apply { value = 0 }
    val weight = MutableLiveData<Long>().apply { value = 0 }
    val unitsConsumed = MutableLiveData<Long>().apply { value = 0 }
    val sweetsPopularityData = MutableLiveData<List<PopularitySweetUi>?>()
    val consumedBarData = MutableLiveData<ConsumedBarData>()
    val sweetsRatingData = MutableLiveData<Map<SweetRating, Long>?>()
    val dateRangeText = MutableLiveData<String>()
    val subDateRangeText = MutableLiveData<String>()
    val unitStringRes: Int by lazy {
        when (unit) {
            MeasurementUnit.GRAM -> R.string.unit_grams
            MeasurementUnit.OUNCE -> R.string.unit_ounces
        }
    }

    private val unit by lazy { sharedPrefs.defaultMeasurementUnit }
    private lateinit var dateRange: DateRange
    var pos: Int = Consts.FIRST_ITEM

    fun setSweets(sweets: List<ConsumedSweetUi>) {
        consumedSweets = sweets
        setPeriod(Periods.WEEK)
    }

    fun setPeriod(period: Periods) {
        if (::dateRange.isInitialized && dateRange.period == period) return
        dateRange = DateRange(period, dateTimeHandler)
        dateRange.advanceRange((pos - Consts.FIRST_ITEM).toLong())

        resetClickRange()
        recalculateConsumedInRange(true)
    }

    fun resetPeriod() {
        clickRange?.let {
            resetClickRange()
            recalculateConsumedInRange(false)
        }
    }

    fun barClicked(barPos: Int) {
        if (clickedBarEmpty(barPos)) {
            resetPeriod()
            return
        }

        val period = clickRange?.period ?: periodFromTimeUnit()
        clickRange = DateRange(period, dateTimeHandler, dateRange.startDate)

        advanceRange(barPos)

        subDateRangeText.postValue(dataGenerator.getSubDateText(clickRange!!, dateTimeHandler))
        recalculateConsumedInRange(false)
    }

    private fun recalculateConsumedInRange(recalculateDataChart: Boolean) {
        consumedInRange = getConsumedInRange(clickRange ?: dateRange, consumedSweets)
        recalculatePeriodLowerPeriod()
        if (recalculateDataChart) {
            recalculateUpperPeriod()
        }
    }

    private fun recalculateUpperPeriod() {
        val barData = dataGenerator.generateConsumedBarData(
            dateRange, dateTimeHandler, consumedInRange)
        calsPerTimeUnits = barData.calsPerTimeUnit

        consumedBarData.postValue(barData)
        dateRangeText.postValue(dateTimeHandler.formattedDateRange(dateRange))
    }

    private fun resetClickRange() {
        clickRange = null
        subDateRangeText.postValue(null)
    }

    private fun recalculatePeriodLowerPeriod() {
        val consumedCals = calsFromConsumed(consumedInRange)
        cals.postValue(consumedCals)
        weight.postValue(weightGFromCals(consumedCals, unit))
        unitsConsumed.postValue(dataGenerator.unitsConsumed(consumedInRange, unit))

        sweetsPopularityData.postValue(sweetPopularityByG(consumedInRange))
        sweetsRatingData.postValue(sweetRatings(consumedInRange))
    }

    private fun clickedBarEmpty(barPos: Int) = calsPerTimeUnits[barPos - 1] == 0L

    private fun advanceRange(periodSelected: Int) {
        if (periodSelected > 1) clickRange!!.advanceRange((periodSelected - 1).toLong())
    }

    private fun periodFromTimeUnit(): Periods {
        return when (dateRange.period) {
            Periods.DAY -> throw IllegalArgumentException("${Periods.DAY} should not be passed here")
            Periods.WEEK -> Periods.DAY
            Periods.MONTH -> Periods.DAY
            Periods.YEAR -> Periods.MONTH
        }
    }
}