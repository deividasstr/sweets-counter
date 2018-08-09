package com.deividasstr.domain.utils

import com.deividasstr.domain.common.UnitTest
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.threeten.bp.LocalDate

class PeriodsTest : UnitTest() {

    private lateinit var period: Periods
    private val startDate: LocalDate = LocalDate.of(2018, 8, 8)

    @Mock
    lateinit var dateTimeHandler: DateTimeHandler

    @Before
    fun setUp() {
        given { dateTimeHandler.currentLocalDate() } willReturn { startDate }
    }

    @Test
    fun dayStart() {
        period = Periods.DAY

        assertEquals(LocalDate.of(2018, 8, 8), period.start(dateTimeHandler))
    }

    @Test
    fun weekStart() {
        period = Periods.WEEK

        assertEquals(LocalDate.of(2018, 8, 6), period.start(dateTimeHandler))
    }

    @Test
    fun monthStart() {
        period = Periods.MONTH
        assertEquals(LocalDate.of(2018, 8, 1), period.start(dateTimeHandler))
    }

    @Test
    fun yearStart() {
        period = Periods.YEAR
        assertEquals(LocalDate.of(2018, 1, 1), period.start(dateTimeHandler))
    }
}