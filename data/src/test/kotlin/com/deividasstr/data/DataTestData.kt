package com.deividasstr.data

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.data.networking.models.ResponseSweet
import com.deividasstr.data.store.models.ConsumedSweetModel
import com.deividasstr.data.store.models.FactModel
import com.deividasstr.data.store.models.SweetModel
import com.deividasstr.domain.utils.DateRange
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class DataTestData {

    companion object {

        val DATETIME = LocalDateTime.of(2018, 5, 27, 10, 10)

        val TEST_LOCALDATE_YESTERDAY = DATETIME.minusDays(1).toLocalDate()
        val TEST_LOCALDATE_TOMORROW = DATETIME.plusDays(1).toLocalDate()

        val YESTERDAY_TOMORROW_DATERANGE = DateRange(
            TEST_LOCALDATE_YESTERDAY, TEST_LOCALDATE_TOMORROW
        )

        val TEST_FACTMODEL_1 = FactModel(12, "Sweets are sweet", 123)
        val TEST_FACTMODEL_2 = FactModel(1, "Sweets are bad", 951358)

        val TEST_FACT_LIST = listOf(TEST_FACTMODEL_1, TEST_FACTMODEL_2)

        val TEST_RESPONSE_FACT1 = ResponseFact(12, "Sweets are sweet", 123)
        val TEST_RESPONSE_FACT2 = ResponseFact(1, "Sweets are bad", 951358)
        val TEST_RESPONSE_FACT_LIST = listOf(TEST_RESPONSE_FACT1, TEST_RESPONSE_FACT2)
        val TEST_TIMESTAMP = 1234567989L

        val TEST_CONSUMED_SWEETMODEL =
            ConsumedSweetModel(1, 2, 100, DATETIME.toEpochSecond(ZoneOffset.UTC))
        val TEST_CONSUMED_SWEETMODEL2 =
            ConsumedSweetModel(2, 3, 65, DATETIME.toEpochSecond(ZoneOffset.UTC))
        val TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW = ConsumedSweetModel(
            3, 1, 100,
            DATETIME.plusDays(2).toEpochSecond(ZoneOffset.UTC)
        )

        val TEST_SWEETMODEL =
            SweetModel(
                2, "Choco", 500.0, 25.0, 50.0, 12.0,
                12.0, 123
            )

        val TEST_SWEETMODEL_ADDED_BY_USER =
            SweetModel(
                2, "Choco", 500.0, 25.0, 50.0, 12.0,
                12.0, 0
            )

        val TEST_SWEETMODEL2 =
            SweetModel(
                3, "Milka", 350.0, 25.0, 50.0, 12.0,
                12.0, 98461298
            )

        val TEST_LIST_SWEETMODELS = listOf(TEST_SWEETMODEL, TEST_SWEETMODEL2)

        val TEST_RESPONSE_SWEET1 = ResponseSweet(
            2, "Choco", 500.0, 25.0, 50.0, 12.0,
            12.0, 123
        )

        val TEST_RESPONSE_SWEET2 = ResponseSweet(
            3, "Milka", 350.0, 25.0, 50.0, 12.0,
            12.0, 98461298
        )

        val TEST_RESPONSE_SWEET_LIST = listOf(TEST_RESPONSE_SWEET1, TEST_RESPONSE_SWEET2)
    }
}
