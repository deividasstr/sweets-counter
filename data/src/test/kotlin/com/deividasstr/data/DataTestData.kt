package com.deividasstr.data

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.data.networking.models.ResponseSweet
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.SweetDb
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class DataTestData {

    companion object {

        val DATETIME = LocalDateTime.of(2018, 5, 27, 10, 10)

        val TEST_LOCALDATE_YESTERDAY = DATETIME.minusDays(1).toLocalDate()
        val TEST_LOCALDATE_TOMORROW = DATETIME.plusDays(1).toLocalDate()

        val TEST_FACTMODEL_1 = FactDb(12, "Sweets are sweet", 123)
        val TEST_FACTMODEL_2 = FactDb(1, "Sweets are bad", 951358)

        val TEST_FACT_LIST = listOf(TEST_FACTMODEL_1, TEST_FACTMODEL_2)

        val TEST_RESPONSE_FACT1 = ResponseFact(12, "Sweets are sweet", 123)
        val TEST_RESPONSE_FACT2 = ResponseFact(1, "Sweets are bad", 951358)
        val TEST_RESPONSE_FACT_LIST = listOf(TEST_RESPONSE_FACT1, TEST_RESPONSE_FACT2)
        val TEST_TIMESTAMP = 1234567989L

        val TEST_CONSUMED_SWEETMODEL =
            ConsumedSweetDb(0, 2, 100, DATETIME.toEpochSecond(ZoneOffset.UTC))
        val TEST_CONSUMED_SWEETMODEL2 =
            ConsumedSweetDb(0, 3, 65, DATETIME.toEpochSecond(ZoneOffset.UTC))
        val TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW = ConsumedSweetDb(
            0, 1, 100,
            DATETIME.plusDays(2).toEpochSecond(ZoneOffset.UTC)
        )

        val TEST_SWEETMODEL =
            SweetDb(
                2, "Choco", 500.0, 25.0, 50.0, 12.0, 8.0,
                12.0
            )

        val TEST_SWEETMODEL_ADDED_BY_USER =
            SweetDb(
                2, "Choco", 500.0, 25.0, 50.0, 12.0, 8.0,
                12.0
            )

        val TEST_SWEETMODEL2 =
            SweetDb(
                3, "Milka", 350.0, 25.0, 50.0, 12.0, 8.0,
                12.0
            )

        val TEST_LIST_SWEETMODELS = listOf(TEST_SWEETMODEL, TEST_SWEETMODEL2)

        val TEST_RESPONSE_SWEET1 = ResponseSweet(
            2, "Choco", 500.0, 25.0, 50.0, 12.0, 8.0,
            12.0
        )

        val TEST_RESPONSE_SWEET2 = ResponseSweet(
            3, "Milka", 350.0, 25.0, 50.0, 12.0, 8.0,
            12.0
        )

        val TEST_RESPONSE_SWEET_LIST = listOf(TEST_RESPONSE_SWEET1, TEST_RESPONSE_SWEET2)
    }
}
