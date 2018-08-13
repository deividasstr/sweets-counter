package com.deividasstr.domain.utils

import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.enums.Periods
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset

class DateTimeHandlerTest : UnitTest() {

    private val dateTimeHandler = DateTimeHandler()

    @Test
    fun formattedDateFull() {
        val date = LocalDate.of(2018, 8, 8).atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        val string = dateTimeHandler.formattedDateFull(date)

        assertEquals("Wednesday, August 8, 2018", string)
    }

    @Test
    fun formattedDateShort() {
        val date = LocalDate.of(2018, 8, 8)
        val string = dateTimeHandler.formattedDateShort(date)

        assertEquals("Aug 8, 2018", string)
    }

    @Test
    fun formattedTime() {
        val date = LocalDateTime.of(2018, 8, 8, 13, 11)
            .toEpochSecond(OffsetDateTime.now().offset)

        val string = dateTimeHandler.formattedTime(date)

        assertEquals("1:11:00 PM", string)
    }

    @Test
    fun formattedDateRange() {
        val dateRange = DateRange(Periods.YEAR, dateTimeHandler).also {
            it.startDate = LocalDate.of(2018, 8, 8)
            it.endDate = LocalDate.of(2018, 8, 9)
        }

        val string = dateTimeHandler.formattedDateRange(dateRange)

        assertEquals("Aug 8, 2018 - Aug 9, 2018", string)
    }
}