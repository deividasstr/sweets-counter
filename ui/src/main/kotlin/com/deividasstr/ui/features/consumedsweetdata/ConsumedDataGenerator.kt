package com.deividasstr.ui.features.consumedsweetdata

import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.MeasurementUnit
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import kotlin.math.roundToLong

object ConsumedDataGenerator {

    const val CALS_PER_G = 7.7
    private const val TOP_SWEETS_OTHER = "OTHER"

    fun calsFromConsumed(consumedSweets: List<ConsumedSweetUi>): Long {
        var cals = 0L
        for (consumedSweet in consumedSweets) {
            val currCals = consumedSweet.g * consumedSweet.sweet.calsPer100 / 100
            cals += currCals
        }
        return cals
    }

    fun unitsConsumed(consumedSweets: List<ConsumedSweetUi>, unit: MeasurementUnit): Long {
        var unitsConsumed = 0L
        for (consumedSweet in consumedSweets) {
            unitsConsumed += consumedSweet.g
        }
        return unitsConsumed / unit.ratioWithGrams
    }

    fun sweetPopularityByG(sweets: List<ConsumedSweetUi>): List<PopularitySweetUi>? {
        if (sweets.isEmpty()) return null

        val nameToConsumedG = sweets
            .asSequence()
            .groupBy { it.sweet.name }
            .map { entry -> entry.key to entry.value.sumBy { it.g } }
            .sortedByDescending { it.second }
            .toList()
            .toMap()

        val totalGConsumed = nameToConsumedG.map { it.value }.sum()

        val dataMap = HashMap<String, Long>()
        for (entry in nameToConsumedG) {
            val name = entry.key
            val amount = entry.value
            val moreThan5Percent = totalGConsumed / amount < 20

            when {
                dataMap.size >= 7 || !moreThan5Percent -> {
                    dataMap[TOP_SWEETS_OTHER] = dataMap[TOP_SWEETS_OTHER]?.plus(amount) ?: amount
                }
                else -> {
                    dataMap[name] = dataMap[name]?.plus(amount) ?: amount
                }
            }
        }

        return dataMap.map { PopularitySweetUi(it.key, it.value) }
    }

    fun sweetRatings(sweets: List<ConsumedSweetUi>): Map<SweetRating, Long>? {
        if (sweets.isEmpty()) return null
        val map = mutableMapOf<SweetRating, Long>()
        for (consumed in sweets) {
            val sweetRating = consumed.sweet.sweetRating()
            if (map.contains(sweetRating)) {
                map[sweetRating] = map[sweetRating]!!.plus(consumed.g)
            } else {
                map[sweetRating] = consumed.g
            }
        }
        return map
    }

    fun weightGFromCals(cals: Long, unit: MeasurementUnit): Long {
        return (cals / CALS_PER_G / unit.ratioWithGrams).roundToLong()
    }

    fun getConsumedInRange(
        dateRange: DateRange,
        consumedSweets: List<ConsumedSweetUi>
    ): List<ConsumedSweetUi> {
        return consumedSweets
            .asSequence()
            .filter { dateRange.contains(it.date) }
            .sortedByDescending { it.g }.toList()
    }

    fun generateConsumedBarData(
        dateRange: DateRange,
        dateTimeHandler: DateTimeHandler,
        sweets: List<ConsumedSweetUi>
    ): ConsumedBarData {

        val composingUnitStart =
            DateRange(dateRange.period.composingUnit, dateTimeHandler, dateRange.startDate)

        val calsPerTimeUnits = when (dateRange.period) {
            Periods.DAY -> throw IllegalArgumentException("Data cannot be generated for ${Periods.DAY}")
            Periods.WEEK -> getCalsInWeek(composingUnitStart, sweets)
            Periods.MONTH -> getCalsInMonth(composingUnitStart, sweets, dateRange)
            Periods.YEAR -> getCalsInYear(composingUnitStart, sweets)
        }
        return ConsumedBarData(calsPerTimeUnits, dateRange.period)
    }

    private fun getCalsInYear(
        composingUnitStart: DateRange,
        sweets: List<ConsumedSweetUi>
    ): LongArray {
        return getCalsInTimeUnit(12, composingUnitStart, sweets)
    }

    private fun getCalsInMonth(
        composingUnitStart: DateRange,
        sweets: List<ConsumedSweetUi>,
        dateRange: DateRange
    ): LongArray {
        val isLeapYear = dateRange.startDate.isLeapYear
        val monthLength = dateRange.startDate.month.length(isLeapYear)
        return getCalsInTimeUnit(monthLength, composingUnitStart, sweets)
    }

    private fun getCalsInWeek(
        composingUnitStart: DateRange,
        sweets: List<ConsumedSweetUi>
    ): LongArray {
        return getCalsInTimeUnit(7, composingUnitStart, sweets)
    }

    private fun getCalsInTimeUnit(
        timeUnitLength: Int,
        composingUnitStart: DateRange,
        sweets: List<ConsumedSweetUi>
    ): LongArray {
        val calsPerUnit = LongArray(timeUnitLength)

        for (unit in 0 until timeUnitLength) {
            val consumedInRange = getConsumedInRange(composingUnitStart, sweets)
            calsPerUnit[unit] = calsFromConsumed(consumedInRange)
            composingUnitStart.advanceRange()
        }
        return calsPerUnit
    }

    fun getSubDateText(clickRange: DateRange, dateTimeHandler: DateTimeHandler): String {
        return when (clickRange.period) {
            Periods.DAY -> dateTimeHandler.formattedDateShort(clickRange.startDate)
            Periods.WEEK -> throw IllegalArgumentException("${Periods.WEEK} should not be passed here")
            Periods.MONTH -> dateTimeHandler.formattedDateRange(clickRange)
            Periods.YEAR -> throw IllegalArgumentException("${Periods.YEAR} should not be passed here")
        }
    }
}

inline fun <T> Iterable<T>.sumBy(selector: (T) -> Long): Long {
    var sum: Long = 0
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
