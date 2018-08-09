package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.domain.utils.DateRange
import com.deividasstr.domain.utils.DateTimeHandler
import com.deividasstr.domain.utils.Periods
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import com.deividasstr.ui.base.models.toSweetUis
import com.deividasstr.ui.features.consumedsweetdata.models.ConsumedBarData
import com.deividasstr.ui.features.consumedsweetdata.models.PopularitySweetUi
import com.deividasstr.ui.features.sweetdetails.SweetRating
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDate
import javax.inject.Inject
import kotlin.math.roundToLong

class ConsumedSweetDataViewModel
@Inject constructor(private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase,
    private val getSweetsByIdsUseCase: GetSweetsByIdsUseCase,
    private val dateTimeHandler: DateTimeHandler) :
    BaseViewModel() {

    companion object {
        const val CALS_PER_G = 7.7
    }

    private var consumedSweets = emptyList<ConsumedSweetUi>()
    private var sweets = emptyList<SweetUi>()

    private lateinit var calsPerTimeUnits: LongArray
    private lateinit var consumedInRange: List<ConsumedSweetUi>

    private var clickRange: DateRange? = null
    private var dateRange = DateRange(Periods.WEEK, dateTimeHandler)

    val dateRangeText = MutableLiveData<String>()
    val cals = MutableLiveData<Long>()
    val weight = MutableLiveData<Long>()
    val sweetsPopularityData = MutableLiveData<List<PopularitySweetUi>>()
    val consumedBarData = MutableLiveData<ConsumedBarData>()
    val sweetsRatingData = MutableLiveData<Map<SweetRating, Int>>()

    init {
        getConsumedSweets()
    }

    fun changePeriod(pos: Int) {
        val period = Periods.values()[pos + 1]
        if (dateRange.period != period) {
            dateRange = DateRange(period, dateTimeHandler)
            resetClickRange()
            recalculateConsumedInRange()
        }
    }

    private fun resetClickRange() {
        if (clickRange != null) clickRange = null
    }

    fun nextRange() {
        dateRange.nextRange()
        resetClickRange()
        recalculateConsumedInRange()
    }

    fun previousRange() {
        dateRange.previousRange()
        resetClickRange()
        recalculateConsumedInRange()
    }

    private fun getConsumedSweets() {
        val disposable = getAllConsumedSweetsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map { it.toConsumedSweetUis() }
            .subscribeBy(onSuccess = { it ->
                val ids = it.map { it.sweetId.toLong() }.toLongArray()
                consumedSweets = it
                getSweets(ids)
            },
                onError = {
                    val ex = it as StringResException
                    if (ex.messageStringRes == com.deividasstr.data.R.string.error_db_no_items) {
                        setError(StringResException(R.string.error_no_consumed_sweets))
                    } else {
                        setError(ex)
                    }
                }
            )
        addDisposable(disposable)
    }

    private fun getSweets(ids: LongArray) {
        val disposable = getSweetsByIdsUseCase.execute(ids)
            .subscribeOn(Schedulers.io())
            .map { it.toSweetUis() }
            .subscribeBy(onSuccess = {
                sweets = it
                recalculateConsumedInRange()
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
    }

    private fun calsFromConsumed(consumedSweets: List<ConsumedSweetUi>): Long {
        var cals = 0L
        for (consumedSweet in consumedSweets) {
            val currCals =
                consumedSweet.g * sweets.find { it.id.toInt() == consumedSweet.sweetId }!!.calsPer100 / 100
            cals += currCals.roundToLong()
        }
        return cals
    }

    private fun sweetPopularityByG(): List<PopularitySweetUi> {
        val map = HashMap<SweetUi, Int>()

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

    private fun sweetRatingPopularityByG(): Map<SweetRating, Int> {
        val map = mutableMapOf(
            Pair(SweetRating.GOOD, 0),
            Pair(SweetRating.AVERAGE, 0),
            Pair(SweetRating.BAD, 0))

        for (consumed in consumedInRange) {
            val sweet = sweets.find { it.id.toInt() == consumed.sweetId }!!
            val sweetRating = sweet.sweetRating()
            map[sweetRating] = map[sweetRating]!!.plus(consumed.g)
        }

        return map
    }

    private fun weightGFromCals(cals: Long): Long {
        return (cals / CALS_PER_G).roundToLong()
    }

    private fun recalculateConsumedInRange() {
        consumedInRange = getConsumedInRange(clickRange ?: dateRange, consumedSweets)
        recalculateDependants()
    }

    private fun getConsumedInRange(dateRange: DateRange,
        consumedSweets: List<ConsumedSweetUi>): List<ConsumedSweetUi> {
        return consumedSweets.filter {
            dateRange.contains(it.date)
        }
    }

    private fun recalculateDependants() {
        val consumedCals = calsFromConsumed(consumedInRange)
        cals.postValue(consumedCals)
        weight.postValue(weightGFromCals(consumedCals))

        sweetsPopularityData.postValue(sweetPopularityByG())
        sweetsRatingData.postValue(sweetRatingPopularityByG())
        if (clickRange == null) {
            consumedBarData.postValue(generateConsumedBarData())
            dateRangeText.postValue(dateTimeHandler.formattedDateRange(dateRange))
        }
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
        val now = LocalDate.now()
        val monthLength = now.month.length(now.isLeapYear)
        return getCalsInTimeUnit(monthLength, Periods.DAY)
    }

    private fun getCalsInWeek(): LongArray {
        return getCalsInTimeUnit(7, Periods.DAY)
    }

    private fun getCalsInTimeUnit(timeUnitLength: Int,
        range: Periods): LongArray {
        val calsPerWeekDay = LongArray(timeUnitLength)
        val dateRange = DateRange(range, dateTimeHandler, dateRange.startDate)

        for (weekday in 0 until timeUnitLength) {
            val consumedInRange = getConsumedInRange(dateRange, consumedInRange)

            calsPerWeekDay[weekday] = calsFromConsumed(consumedInRange)
            dateRange.nextRange()
        }
        return calsPerWeekDay
    }

    fun resetPeriod() {
        clickRange?.let {
            resetClickRange()
            recalculateConsumedInRange()
        }
    }

    fun timeUnitSelected(timeUnitPos: Int) {
        if (calsPerTimeUnits[timeUnitPos - 1] == 0L) {
            resetPeriod()
            return
        }

        val period = clickRange?.period ?: periodFromTimeUnit()
        clickRange = DateRange(period, dateTimeHandler, dateRange.startDate)

        advanceRange(timeUnitPos)
        recalculateConsumedInRange()
    }

    private fun advanceRange(periodSelected: Int) {
        if (periodSelected > 1) clickRange!!.nextRange((periodSelected - 1).toLong())
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