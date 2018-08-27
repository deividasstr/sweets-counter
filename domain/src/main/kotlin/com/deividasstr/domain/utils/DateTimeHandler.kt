package com.deividasstr.domain.utils

import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

// All time calculations happen in epochSeconds!!!
@OpenClass
class DateTimeHandler {

    fun currentEpochSecs(): Long = Instant.now(Clock.systemDefaultZone()).epochSecond
    fun currentLocalDate(): LocalDate = LocalDate.now(ZoneId.systemDefault())

    fun formattedDateFull(seconds: Long): String {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        return zonedDateTime(seconds).format(formatter)
    }

    fun formattedDateShort(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        return localDate.format(formatter)
    }

    fun formattedTime(seconds: Long): String {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        return zonedDateTime(seconds).format(formatter)
    }

    private fun zonedDateTime(seconds: Long): ZonedDateTime {
        val instant = Instant.ofEpochSecond(seconds)
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    fun formattedDateRange(dateRange: DateRange): String {
        return formattedDateShort(dateRange.startDate).plus(" - ")
            .plus(formattedDateShort(dateRange.endDate))
    }

    fun areDatesSameDay(date1: Long, date2: Long): Boolean {
        val dateTime1 = zonedDateTime(date1)
        val dateTime2 = zonedDateTime(date2)

        return with(dateTime1) {
            year == dateTime2.year &&
                monthValue == dateTime2.monthValue &&
                dayOfMonth == dateTime2.dayOfMonth
        }
    }
}