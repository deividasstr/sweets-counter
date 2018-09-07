package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.enums.MeasurementUnit
import com.deividasstr.domain.enums.Periods
import com.deividasstr.domain.utils.DateRange
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts
import com.deividasstr.ui.features.sweetdetails.SweetRating
import javax.inject.Inject
import kotlin.math.roundToLong

class ConsumedDataPeriodViewModel
@Inject constructor(
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    companion object {
        const val CALS_PER_G = 7.7
        const val TOP_SWEETS_OTHER = "OTHER"
    }

    lateinit var consumedSweets: List<ConsumedSweetUi>

    private lateinit var calsPerTimeUnits: LongArray
    private lateinit var consumedInRange: List<ConsumedSweetUi>

    private var clickRange: DateRange? = null

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

    private fun calsFromConsumed(consumedSweets: List<ConsumedSweetUi>): Long {
        var cals = 0L

        for (consumedSweet in consumedSweets) {
            val currCals = consumedSweet.g * consumedSweet.sweet.calsPer100 / 100
            cals += currCals
        }
        return cals
    }

    private fun unitsConsumed(consumedSweets: List<ConsumedSweetUi>): Long {
        var unitsConsumed = 0L
        for (consumedSweet in consumedSweets) {
            unitsConsumed += consumedSweet.g
        }
        return unitsConsumed / unit.ratioWithGrams
    }

    private fun sweetPopularityByG(): List<PopularitySweetUi>? {
        if (consumedInRange.isEmpty()) return null
        val map = HashMap<String, Long>()
        consumedInRange = consumedInRange.sortedByDescending { it.g }

        val totalG: Int = consumedInRange.sumBy { it.g.toInt() }

        consumedInRange.forEach { consumed ->
            val sweet = consumed.sweet
            val moreThan5Percent = totalG / consumed.g < 20

            when {
                map.size >= 7 || !moreThan5Percent -> {
                    map[TOP_SWEETS_OTHER] = map[TOP_SWEETS_OTHER]?.plus(consumed.g) ?: consumed.g
                }
                else -> {
                    map[sweet.name] = map[sweet.name]?.plus(consumed.g) ?: consumed.g
                }
            }
        }

        return map.map { PopularitySweetUi(it.key, it.value) }
    }

    private fun sweetRatings(): Map<SweetRating, Long>? {
        if (consumedInRange.isEmpty()) return null
        val map = mutableMapOf<SweetRating, Long>()
        for (consumed in consumedInRange) {
            val sweetRating = consumed.sweet.sweetRating()
            if (map.contains(sweetRating)) {
                map[sweetRating] = map[sweetRating]!!.plus(consumed.g)
            } else {
                map[sweetRating] = consumed.g
            }
        }
        return map
    }

    private fun weightGFromCals(cals: Long): Long {
        return (cals / CALS_PER_G / unit.ratioWithGrams).roundToLong()
    }

    private fun recalculateConsumedInRange(recalculateDataChart: Boolean) {
        consumedInRange = getConsumedInRange(clickRange ?: dateRange, consumedSweets)
        recalculatePeriodLowerPeriod()
        if (recalculateDataChart) {
            recalculateUpperPeriod()
        }
    }

    private fun recalculateUpperPeriod() {
        consumedBarData.postValue(generateConsumedBarData())
        dateRangeText.postValue(dateTimeHandler.formattedDateRange(dateRange))
    }

    private fun getConsumedInRange(dateRange: DateRange, consumedSweets: List<ConsumedSweetUi>): List<ConsumedSweetUi> {
        return consumedSweets.filter {
            dateRange.contains(it.date)
        }
    }

    private fun resetClickRange() {
        clickRange = null
        subDateRangeText.postValue(null)
    }

    private fun recalculatePeriodLowerPeriod() {
        val consumedCals = calsFromConsumed(consumedInRange)
        cals.postValue(consumedCals)
        weight.postValue(weightGFromCals(consumedCals))
        unitsConsumed.postValue(unitsConsumed(consumedInRange))

        sweetsPopularityData.postValue(sweetPopularityByG())
        sweetsRatingData.postValue(sweetRatings())
    }

    private fun generateConsumedBarData(): ConsumedBarData {
        calsPerTimeUnits = when (dateRange.period) {
            Periods.DAY -> throw IllegalArgumentException("Data cannot be generated for ${Periods.DAY}")
            Periods.WEEK -> getCalsInWeek()
            Periods.MONTH -> getCalsInMonth()
            Periods.YEAR -> getCalsInYear()
        }
        return ConsumedBarData(calsPerTimeUnits, dateRange.period)
    }

    private fun getCalsInYear(): LongArray {
        return getCalsInTimeUnit(12, Periods.MONTH)
    }

    private fun getCalsInMonth(): LongArray {
        val isLeapYear = dateRange.startDate.isLeapYear
        val monthLength = dateRange.startDate.month.length(isLeapYear)
        return getCalsInTimeUnit(monthLength, Periods.DAY)
    }

    private fun getCalsInWeek(): LongArray {
        return getCalsInTimeUnit(7, Periods.DAY)
    }

    private fun getCalsInTimeUnit(
        timeUnitLength: Int,
        range: Periods
    ): LongArray {
        val calsPerWeekDay = LongArray(timeUnitLength)
        val dateRange = DateRange(range, dateTimeHandler, dateRange.startDate)

        for (weekday in 0 until timeUnitLength) {
            val consumedInRange = getConsumedInRange(dateRange, consumedInRange)

            calsPerWeekDay[weekday] = calsFromConsumed(consumedInRange)
            dateRange.advanceRange()
        }
        return calsPerWeekDay
    }

    fun resetPeriod() {
        clickRange?.let {
            resetClickRange()
            recalculateConsumedInRange(false)
        }
    }

    fun barClicked(barPos: Int) {
        if (calsPerTimeUnits[barPos - 1] == 0L) {
            resetPeriod()
            return
        }

        val period = clickRange?.period ?: periodFromTimeUnit()
        clickRange = DateRange(period, dateTimeHandler, dateRange.startDate)

        advanceRange(barPos)

        subDateRangeText.postValue(dateTimeHandler.formattedDateRange(clickRange!!))

        recalculateConsumedInRange(false)
    }

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

    fun setSweets(sweets: List<ConsumedSweetUi>) {
        consumedSweets = sweets
    }

    fun setPeriod(period: Periods) {
        dateRange = DateRange(period, dateTimeHandler)
        dateRange.advanceRange((pos - Consts.FIRST_ITEM).toLong())

        resetClickRange()
        recalculateConsumedInRange(true)
    }
}