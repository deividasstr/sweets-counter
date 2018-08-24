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
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.consumedsweetdata.utils.Consts
import com.deividasstr.ui.features.sweetdetails.SweetRating
import org.threeten.bp.LocalDate
import javax.inject.Inject
import kotlin.math.roundToLong

class ConsumedDataPeriodViewModel
@Inject constructor(
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    companion object {
        const val CALS_PER_G = 7.7
    }

    private lateinit var consumedSweets: List<ConsumedSweetUi>
    private lateinit var sweets: List<SweetUi>

    private lateinit var calsPerTimeUnits: LongArray
    private lateinit var consumedInRange: List<ConsumedSweetUi>

    private var clickRange: DateRange? = null

    val cals = MutableLiveData<Long>()
    val weight = MutableLiveData<Long>()
    val unitsConsumed = MutableLiveData<Long>()
    val sweetsPopularityData = MutableLiveData<List<PopularitySweetUi>>()
    val consumedBarData = MutableLiveData<ConsumedBarData>()
    val sweetsRatingData = MutableLiveData<Map<SweetRating, Long>>()
    val dateRangeText = MutableLiveData<String>()

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
            val currCals =
                consumedSweet.g * sweets.find { it.id.toInt() == consumedSweet.sweetId }!!.calsPer100 / 100
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

    private fun sweetPopularityByG(): List<PopularitySweetUi> {
        val map = HashMap<SweetUi, Long>()
        for (consumed in consumedInRange) {
            val sweet = sweets.find { it.id.toInt() == consumed.sweetId }!!

            if (map.containsKey(sweet)) {
                map[sweet] = map[sweet]!!.plus(consumed.g)
            } else {
                map[sweet] = consumed.g
            }
        }

        val list = mutableListOf<PopularitySweetUi>()
        map.asIterable().forEach {
            list.add(
                PopularitySweetUi(
                    it.key,
                    it.value))
        }
        return list
    }

    private fun sweetRatingPopularityByG(): Map<SweetRating, Long> {
        val map = mutableMapOf(
            Pair(SweetRating.GOOD, 0L),
            Pair(SweetRating.AVERAGE, 0L),
            Pair(SweetRating.BAD, 0L))

        for (consumed in consumedInRange) {
            val sweet = sweets.find { it.id.toInt() == consumed.sweetId }!!
            val sweetRating = sweet.sweetRating()
            map[sweetRating] = map[sweetRating]!!.plus(consumed.g)
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

    private fun getConsumedInRange(
        dateRange: DateRange,
        consumedSweets: List<ConsumedSweetUi>
    ): List<ConsumedSweetUi> {
        return consumedSweets.filter {
            dateRange.contains(it.date)
        }
    }

    private fun resetClickRange() {
        clickRange = null
    }

    private fun recalculatePeriodLowerPeriod() {
        val consumedCals = calsFromConsumed(consumedInRange)
        cals.postValue(consumedCals)
        weight.postValue(weightGFromCals(consumedCals))
        unitsConsumed.postValue(unitsConsumed(consumedInRange))

        sweetsPopularityData.postValue(sweetPopularityByG())
        sweetsRatingData.postValue(sweetRatingPopularityByG())
    }

    private fun generateConsumedBarData(): ConsumedBarData {
        calsPerTimeUnits = when (dateRange.period) {
            Periods.DAY -> throw IllegalArgumentException("Data cannot be generated for ${Periods.DAY}")
            Periods.WEEK -> getCalsInWeek()
            Periods.MONTH -> getCalsInMonth()
            Periods.YEAR -> getCalsInYear()
        }
        return ConsumedBarData(calsPerTimeUnits, dateRange.period, noData(calsPerTimeUnits))
    }

    private fun noData(calsPerTimeUnits: LongArray): Boolean {
        return calsPerTimeUnits.max() == 0L
    }

    private fun getCalsInYear(): LongArray {
        return getCalsInTimeUnit(12, Periods.MONTH)
    }

    private fun getCalsInMonth(): LongArray {
        val now = LocalDate.now()
        val monthLength = now.month.length(now.isLeapYear)
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

    fun setSweets(pair: Pair<List<ConsumedSweetUi>, List<SweetUi>>) {
        consumedSweets = pair.first
        sweets = pair.second
    }

    fun setPeriod(period: Periods) {
        dateRange = DateRange(period, dateTimeHandler)
        dateRange.advanceRange((pos - Consts.FIRST_ITEM).toLong())

        resetClickRange()
        recalculateConsumedInRange(true)
    }
}