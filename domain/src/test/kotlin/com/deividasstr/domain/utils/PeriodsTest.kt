package com.deividasstr.domain.utils

import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.testutils.UnitTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import org.amshove.kluent.shouldEqual
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

        period.start(dateTimeHandler) shouldEqual LocalDate.of(2018, 8, 8)
    }

    @Test
    fun weekStart() {
        period = Periods.WEEK

        period.start(dateTimeHandler) shouldEqual LocalDate.of(2018, 8, 6)
    }

    @Test
    fun monthStart() {
        period = Periods.MONTH
        period.start(dateTimeHandler) shouldEqual LocalDate.of(2018, 8, 1)
    }

    @Test
    fun yearStart() {
        period = Periods.YEAR
        period.start(dateTimeHandler) shouldEqual LocalDate.of(2018, 1, 1)
    }
}
