package com.deividasstr.domain.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalUnit

enum class Periods {

    DAY {
        override val timeUnit: TemporalUnit = ChronoUnit.DAYS
        override fun start(dateTimeHandler: DateTimeHandler): LocalDate {
            return dateTimeHandler.currentLocalDate()
        }
    },
    WEEK {
        override val timeUnit: TemporalUnit = ChronoUnit.WEEKS
        override fun start(dateTimeHandler: DateTimeHandler): LocalDate {
            val now = dateTimeHandler.currentLocalDate()
            val weekday = now.dayOfWeek.value
            val daysFromMonday = weekday - 1
            return now.minusDays(daysFromMonday.toLong())
        }
    },
    MONTH {
        override val timeUnit: TemporalUnit = ChronoUnit.MONTHS
        override fun start(dateTimeHandler: DateTimeHandler): LocalDate {
            val now = dateTimeHandler.currentLocalDate()
            return now.withDayOfMonth(1)
        }
    },
    YEAR {
        override val timeUnit: TemporalUnit = ChronoUnit.YEARS
        override fun start(dateTimeHandler: DateTimeHandler): LocalDate {
            val now = dateTimeHandler.currentLocalDate()
            return now.withMonth(1).withDayOfMonth(1)
        }
    };

    abstract val timeUnit: TemporalUnit
    abstract fun start(dateTimeHandler: DateTimeHandler): LocalDate
}
