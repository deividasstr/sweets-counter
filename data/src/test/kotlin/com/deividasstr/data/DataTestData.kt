package com.deividasstr.data

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.data.networking.models.ResponseSweet
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.domain.common.TestData
import org.threeten.bp.ZoneOffset

class DataTestData {

    companion object {
        private val fact1 = TestData.TEST_FACT_1
        private val fact2 = TestData.TEST_FACT_2

        val TEST_FACTMODEL_1 = FactDb(fact1)
        val TEST_FACTMODEL_2 = FactDb(fact2)

        val TEST_FACT_LIST = listOf(TEST_FACTMODEL_1, TEST_FACTMODEL_2)

        val TEST_RESPONSE_FACT1 = ResponseFact(fact1.id, fact1.text, 123)
        val TEST_RESPONSE_FACT2 = ResponseFact(fact2.id, fact2.text, 951358)
        val TEST_RESPONSE_FACT_LIST = listOf(TEST_RESPONSE_FACT1, TEST_RESPONSE_FACT2)
        val TEST_TIMESTAMP = 1234567989L

        private val consumed1 = TestData.TEST_CONSUMED_SWEET
        private val consumed2 = TestData.TEST_CONSUMED_SWEET2

        val TEST_CONSUMED_SWEETMODEL =
            ConsumedSweetDb(consumed1)
        val TEST_CONSUMED_SWEETMODEL2 =
            ConsumedSweetDb(consumed2)
        val TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW = ConsumedSweetDb(
            0, consumed1.sweetId, consumed1.g,
            TestData.LOCAL_DATE_TIME.plusDays(2).toEpochSecond(ZoneOffset.UTC)
        )

        private val sweet1 = TestData.TEST_SWEET
        private val sweet2 = TestData.TEST_SWEET2

        val TEST_SWEETMODEL = SweetDb(sweet1)
        val TEST_SWEETMODEL2 = SweetDb(sweet2)
        val TEST_LIST_SWEETMODELS = listOf(TEST_SWEETMODEL, TEST_SWEETMODEL2)

        val TEST_RESPONSE_SWEET1 = ResponseSweet(
            sweet1.id, sweet1.name, sweet1.calsPer100, sweet1.servingG, sweet1.fatG, sweet1.carbsG,
            sweet1.sugarG, sweet1.proteinG
        )

        val TEST_RESPONSE_SWEET2 = ResponseSweet(
            sweet2.id, sweet2.name, sweet2.calsPer100, sweet2.servingG, sweet2.fatG, sweet2.carbsG,
            sweet2.sugarG, sweet2.proteinG
        )

        val TEST_RESPONSE_SWEET_LIST = listOf(TEST_RESPONSE_SWEET1, TEST_RESPONSE_SWEET2)
    }
}
