package com.deividasstr.domain.utils

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

@OpenClass
class DateTimeHandler {

    fun currentDateTimeMillis(): Long = Instant.now().toEpochMilli()

    fun formattedDate(millis: Long): String {
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        return zonedDateTime(millis).format(formatter)
    }

    fun formattedTime(millis: Long): String {
        val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)
        return zonedDateTime(millis).format(formatter)
    }

    private fun zonedDateTime(millis: Long): ZonedDateTime {
        val instant = Instant.ofEpochMilli(millis)
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
}