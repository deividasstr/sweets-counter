package com.deividasstr.domain.common

import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.utils.DateRange
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class TestData {

    companion object {

        val DATETIME = LocalDateTime.of(2018, 5, 27, 10, 10)

        val TEST_CONSUMED_SWEET =
            ConsumedSweet(1, 2, 100, DATETIME.toEpochSecond(ZoneOffset.UTC))
        val TEST_CONSUMED_SWEET2 =
            ConsumedSweet(2, 3, 65, DATETIME.toEpochSecond(ZoneOffset.UTC))

        val TEST_SWEET = Sweet(2, "Choco", 500.0, 25.0, 50.0, 12.0, 12.0)
        val TEST_SWEET2 = Sweet(3, "Milka", 350.0, 25.0, 50.0, 12.0, 12.0)

        val TEST_TOTAL_CALS = 3000

        val TEST_LIST_CONSUMED_SWEETS = listOf(TEST_CONSUMED_SWEET, TEST_CONSUMED_SWEET)
        val TEST_LIST_CONSUMED_SWEETS_EMPTY = listOf<ConsumedSweet>()
        val TEST_ID: Long = 1
        val TEST_FACT_1 = Fact(12, "Sweets are sweet")
        val TEST_FACT_2 = Fact(1, "Sweets are bad")

        val TEST_LIST_SWEETS = listOf(TEST_SWEET, TEST_SWEET2)
        val TEST_SWEET_NAME_SEARCH = "Choco coconut bun"
        val TEST_DATERANGE: DateRange =
            DateRange(DATETIME.toLocalDate(), DATETIME.plusDays(1).toLocalDate())
    }
}