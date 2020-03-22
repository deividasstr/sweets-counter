package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.calsFromConsumed
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.getConsumedInRange
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.sweetPopularityByG
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.sweetRatings
import com.deividasstr.ui.features.consumedsweetdata.ConsumedDataGenerator.weightGFromCals
import com.deividasstr.ui.features.consumedsweetdata.models.LowerPeriodModel
import com.deividasstr.ui.features.consumedsweetdata.models.UpperPeriodModel
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts
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
    val upperPeriodModel = MutableLiveData<UpperPeriodModel>()
    val lowerPeriodModel = MutableLiveData<LowerPeriodModel>()
        .apply { value = LowerPeriodModel() }

    private val unit by lazy { sharedPrefs.defaultMeasurementUnit }

    private val unitStringRes: Int by lazy {
        when (unit) {
            MeasurementUnit.GRAM -> R.string.unit_grams
            MeasurementUnit.OUNCE -> R.string.unit_ounces
        }
    }

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

    fun toggleSubPeriod(barPos: Int) {
        if (clickedBarEmpty(barPos)) {
            resetPeriod()
            return
        }

        val period = clickRange?.period ?: periodFromTimeUnit()
        clickRange = DateRange(period, dateTimeHandler, dateRange.startDate)

        advanceRange(barPos)

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

        val dateRangeText = dateTimeHandler.formattedDateRange(dateRange)
        val newUpperPeriodModel = UpperPeriodModel(dateRangeText, barData)

        upperPeriodModel.postValue(newUpperPeriodModel)
    }

    private fun resetClickRange() {
        clickRange = null
    }

    private fun recalculatePeriodLowerPeriod() {
        val consumedCals = calsFromConsumed(consumedInRange)
        val weight = weightGFromCals(consumedCals, unit)
        val unitsConsumed = dataGenerator.unitsConsumed(consumedInRange, unit)

        val sweetsPopularityData = sweetPopularityByG(consumedInRange)
        val sweetsRatingData = sweetRatings(consumedInRange)

        var subDateRangeText: String? = null
        clickRange?.let {
            subDateRangeText = dataGenerator.getSubDateText(it, dateTimeHandler)
        }

        val newLowerPeriodModel = LowerPeriodModel(
            consumedCals,
            weight,
            unitsConsumed,
            sweetsPopularityData,
            sweetsRatingData,
            subDateRangeText,
            unitStringRes)

        lowerPeriodModel.postValue(newLowerPeriodModel)
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
