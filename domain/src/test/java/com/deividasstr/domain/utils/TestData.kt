package com.deividasstr.domain.utils

import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.entities.Fact
import com.deividasstr.domain.entities.Sweet
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId


class TestData {
    companion object {
        val TEST_CONSUMED_SWEET = ConsumedSweet(1, 1, 100, Instant.now().epochSecond)
        val TEST_SWEET = Sweet(2, "Choco", 500, 25, 50, 12)
        val TEST_TOTAL_CALS = 3000

        val TEST_DATERANGE = DateRange(
                LocalDate.now(ZoneId.of("Asia/Nicosia")),
                LocalDate.now(ZoneId.of("Asia/Nicosia"))
                        .plusDays(2))
        val TEST_LIST_CONSUMED_SWEETS = listOf(TEST_CONSUMED_SWEET, TEST_CONSUMED_SWEET)
        val TEST_LIST_CONSUMED_SWEETS_EMPTY = listOf<ConsumedSweet>()
        val TEST_ID = 1
        val TEST_FACT_1 = Fact(12, "Sweets are sweet")
        val TEST_FACT_2 = Fact(1, "Sweets are bad")

        val TEST_LIST_SWEETS = listOf(TEST_SWEET, TEST_SWEET)
        val TEST_SWEET_NAME_SEARCH = "Choco coconut bun"
    }
}