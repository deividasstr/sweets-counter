package com.deividasstr.data

import com.deividasstr.data.networking.models.ResponseFact
import com.deividasstr.data.networking.models.ResponseSweet
import com.deividasstr.data.store.dbs.DbFact
import com.deividasstr.data.store.dbs.DbSweet
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.testutils.TestData
import org.threeten.bp.ZoneOffset

object DataTestData {

    private val fact1 = TestData.TEST_FACT_1
    private val fact2 = TestData.TEST_FACT_2

    val TEST_FACTMODEL_1 = DbFact(fact1)
    val TEST_FACTMODEL_2 = DbFact(fact2)

    val TEST_FACT_LIST = listOf(TEST_FACTMODEL_1, TEST_FACTMODEL_2)

    val TEST_RESPONSE_FACT1 = ResponseFact(fact1.id, fact1.text, 123)
    val TEST_RESPONSE_FACT2 = ResponseFact(fact2.id, fact2.text, 951358)
    val TEST_RESPONSE_FACT_LIST = listOf(TEST_RESPONSE_FACT1, TEST_RESPONSE_FACT2)
    val TEST_TIMESTAMP = 1234567989L

    val TEST_CONSUMED_SWEETMODEL3_DAY_AFTER_TOMORROW = ConsumedSweet(
        TestData.TEST_CONSUMED_SWEET.grams,
        TestData.LOCAL_DATE_TIME.plusDays(2).toEpochSecond(ZoneOffset.UTC),
        TestData.TEST_CONSUMED_SWEET.sweet
    )

    private val sweet1 = TestData.TEST_SWEET
    private val sweet2 = TestData.TEST_SWEET2

    val TEST_SWEETMODEL = DbSweet(sweet1)
    val TEST_SWEETMODEL2 = DbSweet(sweet2)
    val TEST_LIST_SWEETMODELS = listOf(TEST_SWEETMODEL, TEST_SWEETMODEL2)

    val TEST_RESPONSE_SWEET1 = ResponseSweet(
        sweet1.id, sweet1.name, sweet1.calsPer100, sweet1.fatG, sweet1.carbsG,
        sweet1.sugarG, sweet1.proteinG
    )

    val TEST_RESPONSE_SWEET2 = ResponseSweet(
        sweet2.id, sweet2.name, sweet2.calsPer100, sweet2.fatG, sweet2.carbsG,
        sweet2.sugarG, sweet2.proteinG
    )

    val TEST_RESPONSE_SWEET_LIST = listOf(TEST_RESPONSE_SWEET1, TEST_RESPONSE_SWEET2)
}
