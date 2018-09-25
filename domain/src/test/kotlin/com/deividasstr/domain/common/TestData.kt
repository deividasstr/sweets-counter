package com.deividasstr.domain.common

import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.domain.entities.DateRange
import com.deividasstr.domain.entities.DateTimeHandler
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime

class TestData {

    companion object {
        val TEST_SWEET = Sweet(2, "Choco", 500, 25.0, 12.0, 8.0, 12.0)
        val TEST_SWEET2 = Sweet(3, "Milka", 350, 25.0, 12.0, 8.0, 12.0)
        val TEST_SWEET3 = Sweet(8, "Raisins", 150, 25.0, 12.0, 8.0, 12.0)

        val LOCAL_DATE_TIME = LocalDateTime.of(2018, 8, 8, 17, 15)!!
        val CURRENT_EPOCH_SECS = LOCAL_DATE_TIME.toEpochSecond(OffsetDateTime.now().offset)

        val TEST_CONSUMED_SWEET =
            ConsumedSweet(0, 2, 100, CURRENT_EPOCH_SECS, TEST_SWEET)
        val TEST_CONSUMED_SWEET2 = ConsumedSweet(0, 3, 65, CURRENT_EPOCH_SECS.minus(2 * 60 * 60), TEST_SWEET2)
        val TEST_CONSUMED_SWEET3 = ConsumedSweet(
            0,
            2,
            12,
            LocalDateTime.of(2018, 8, 6, 17, 15)!!.toEpochSecond(OffsetDateTime.now().offset), TEST_SWEET)
        val TEST_CONSUMED_SWEET4 = ConsumedSweet(
            0,
            3,
            120,
            LocalDateTime.of(2018, 8, 5, 17, 15)!!.toEpochSecond(OffsetDateTime.now().offset), TEST_SWEET2)
        val TEST_CONSUMED_SWEET5 = ConsumedSweet(
            0,
            2,
            66,
            LocalDateTime.of(2018, 7, 6, 17, 15)!!.toEpochSecond(OffsetDateTime.now().offset), TEST_SWEET)
        val TEST_CONSUMED_SWEET6 = ConsumedSweet(
            0,
            3,
            90,
            LocalDateTime.of(2017, 8, 6, 17, 15)!!.toEpochSecond(OffsetDateTime.now().offset), TEST_SWEET2)

        val TEST_TOTAL_CALS = 3000L

        val TEST_LIST_CONSUMED_SWEETS = listOf(TEST_CONSUMED_SWEET, TEST_CONSUMED_SWEET)
        val TEST_LIST_CONSUMED_SWEETS2 = listOf(TEST_CONSUMED_SWEET, TEST_CONSUMED_SWEET2)
        val TEST_LIST_CONSUMED_SWEETS3 = listOf(
            TEST_CONSUMED_SWEET,
            TEST_CONSUMED_SWEET2,
            TEST_CONSUMED_SWEET3,
            TEST_CONSUMED_SWEET4,
            TEST_CONSUMED_SWEET5,
            TEST_CONSUMED_SWEET6)

        val TEST_LIST_CONSUMED_SWEETS_DIFFERENT_PERIODS =
            listOf(TEST_CONSUMED_SWEET, TEST_CONSUMED_SWEET2)
        val TEST_LIST_CONSUMED_SWEETS_EMPTY = listOf<ConsumedSweet>()
        val TEST_ID: Long = 1
        val TEST_FACT_1 = Fact(12, "Sweets are sweet")
        val TEST_FACT_2 = Fact(1, "Sweets are bad")

        val TEST_LIST_SWEETS = listOf(TEST_SWEET, TEST_SWEET2)
        val TEST_SWEET_NAME_SEARCH = "Choco coconut bun"
        val TEST_DATERANGE: DateRange =
            DateRange(Periods.WEEK, DateTimeHandler())
    }
}