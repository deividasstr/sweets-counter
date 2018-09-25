package com.deividasstr.domain.utils

import com.deividasstr.domain.common.UnitTest
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.Periods
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.threeten.bp.LocalDate

class DateRangeTest : UnitTest() {

    private lateinit var range: DateRange

    @Mock
    lateinit var dateTimeHandler: DateTimeHandler

    @Before
    fun setUp() {
        given { dateTimeHandler.currentLocalDate() } willReturn {
            LocalDate.of(2018, 8, 8)
        }
    }

    @Test
    fun dayNextRange() {
        range = DateRange(Periods.DAY, dateTimeHandler)
        range.advanceRange()

        assertEquals(LocalDate.of(2018, 8, 9), range.startDate)
        assertEquals(LocalDate.of(2018, 8, 9), range.endDate)
    }

    @Test
    fun dayNextRangex3() {
        range = DateRange(Periods.DAY, dateTimeHandler)
        range.advanceRange(3)

        assertEquals(LocalDate.of(2018, 8, 11), range.startDate)
        assertEquals(LocalDate.of(2018, 8, 11), range.endDate)
    }

    @Test
    fun dayPreviousRange() {
        range = DateRange(Periods.DAY, dateTimeHandler)
        range.advanceRange(-1)

        assertEquals(LocalDate.of(2018, 8, 7), range.startDate)
        assertEquals(LocalDate.of(2018, 8, 7), range.endDate)
    }

    @Test
    fun weekNextRange() {
        range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange()

        assertEquals(LocalDate.of(2018, 8, 13), range.startDate)
        assertEquals(LocalDate.of(2018, 8, 19), range.endDate)
    }

    @Test
    fun weekNextRangex3() {
        range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange(3)

        assertEquals(LocalDate.of(2018, 8, 27), range.startDate)
        assertEquals(LocalDate.of(2018, 9, 2), range.endDate)
    }

    @Test
    fun weekPreviousRange() {
        range = DateRange(Periods.WEEK, dateTimeHandler)
        range.advanceRange(-1)

        assertEquals(LocalDate.of(2018, 7, 30), range.startDate)
        assertEquals(LocalDate.of(2018, 8, 5), range.endDate)
    }

    @Test
    fun monthNextRange() {
        range = DateRange(Periods.MONTH, dateTimeHandler)
        range.advanceRange()

        assertEquals(LocalDate.of(2018, 9, 1), range.startDate)
        assertEquals(LocalDate.of(2018, 9, 30), range.endDate)
    }

    @Test
    fun monthNextRangex3() {
        range = DateRange(Periods.MONTH, dateTimeHandler)
        range.advanceRange(3)

        assertEquals(LocalDate.of(2018, 11, 1), range.startDate)
        assertEquals(LocalDate.of(2018, 11, 30), range.endDate)
    }

    @Test
    fun monthPreviousRange() {
        range = DateRange(Periods.MONTH, dateTimeHandler)
        range.advanceRange(-1)

        assertEquals(LocalDate.of(2018, 7, 1), range.startDate)
        assertEquals(LocalDate.of(2018, 7, 31), range.endDate)
    }

    @Test
    fun yearNextRange() {
        range = DateRange(Periods.YEAR, dateTimeHandler)
        range.advanceRange()

        assertEquals(LocalDate.of(2019, 1, 1), range.startDate)
        assertEquals(LocalDate.of(2019, 12, 31), range.endDate)
    }

    @Test
    fun yearNextRangex3() {
        range = DateRange(Periods.YEAR, dateTimeHandler)
        range.advanceRange(3)

        assertEquals(LocalDate.of(2021, 1, 1), range.startDate)
        assertEquals(LocalDate.of(2021, 12, 31), range.endDate)
    }

    @Test
    fun yearPreviousRange() {
        range = DateRange(Periods.YEAR, dateTimeHandler)
        range.advanceRange(-1)

        assertEquals(LocalDate.of(2017, 1, 1), range.startDate)
        assertEquals(LocalDate.of(2017, 12, 31), range.endDate)
    }
}