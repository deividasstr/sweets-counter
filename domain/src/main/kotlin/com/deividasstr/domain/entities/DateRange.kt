package com.deividasstr.domain.entities

import com.deividasstr.domain.entities.enums.Periods
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime

class DateRange(
    val period: Periods,
    dateTimeHandler: DateTimeHandler,
    customStartDate: LocalDate? = null
) : ClosedRange<Long> {

    override var start: Long = 0
        get() = startDate.atStartOfDay().toEpochSecond(OffsetDateTime.now().offset)

    override var endInclusive: Long = 0
        get() = endDate.plusDays(1).atStartOfDay().toEpochSecond(OffsetDateTime.now().offset)

    var startDate: LocalDate
    var endDate: LocalDate

    init {
        startDate = customStartDate ?: period.start(dateTimeHandler)
        endDate = addOneTimeUnit(startDate)
    }

    override fun contains(value: Long): Boolean = value in start..endInclusive

    fun advanceRange(times: Long = 1) {
        startDate = startDate.plus(times, period.timeUnit)
        endDate = endDate.plus(times, period.timeUnit)
    }

    // For end date, to be inclusive
    private fun addOneTimeUnit(date: LocalDate): LocalDate {
        return date.plus(1, period.timeUnit).minusDays(1)
    }

    override fun equals(other: Any?): Boolean {
        return period == (other as DateRange).period &&
            startDate == other.startDate &&
            endDate == other.endDate
    }

    override fun toString(): String {
        return "start $startDate $start end $endDate $endInclusive"
    }

    // Overridden only due to lint warning
    override fun hashCode(): Int {
        var result = period.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + endInclusive.hashCode()
        return result
    }
}