package com.deividasstr.domain.enums

import com.deividasstr.domain.utils.DateTimeHandler
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalUnit

enum class Periods {

    DAY {
        override val composingUnit: Periods = DAY

        override fun startFromDate(dateTimeHandler: DateTimeHandler, date: LocalDate): LocalDate {
            return date
        }

        override val timeUnit: TemporalUnit = ChronoUnit.DAYS
    },
    WEEK {
        override val composingUnit: Periods = DAY
        override fun startFromDate(dateTimeHandler: DateTimeHandler, date: LocalDate): LocalDate {
            val weekday = date.dayOfWeek.value
            val daysFromMonday = weekday - 1
            return date.minusDays(daysFromMonday.toLong())
        }

        override val timeUnit: TemporalUnit = ChronoUnit.WEEKS
    },
    MONTH {
        override val composingUnit: Periods = DAY
        override fun startFromDate(dateTimeHandler: DateTimeHandler, date: LocalDate): LocalDate {
            return date.withDayOfMonth(1)
        }

        override val timeUnit: TemporalUnit = ChronoUnit.MONTHS
    },
    YEAR {
        override val composingUnit: Periods = MONTH
        override fun startFromDate(dateTimeHandler: DateTimeHandler, date: LocalDate): LocalDate {
            return date.withMonth(1).withDayOfMonth(1)
        }

        override val timeUnit: TemporalUnit = ChronoUnit.YEARS
    };

    abstract val composingUnit: Periods
    abstract val timeUnit: TemporalUnit
    abstract fun startFromDate(dateTimeHandler: DateTimeHandler, date: LocalDate): LocalDate

    fun start(dateTimeHandler: DateTimeHandler): LocalDate {
        return startFromDate(dateTimeHandler, dateTimeHandler.currentLocalDate())
    }
}
