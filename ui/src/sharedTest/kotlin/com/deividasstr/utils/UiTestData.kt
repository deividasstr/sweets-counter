package com.deividasstr.utils

import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.SweetDb

import com.deividasstr.testutils.TestData
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.FactUi
import com.deividasstr.ui.base.models.SweetUi

class UiTestData {
    companion object {
        val UI_SWEET1 = SweetUi(TestData.TEST_SWEET)
        val UI_SWEET2 = SweetUi(TestData.TEST_SWEET2)
        val UI_SWEET3 = SweetUi(TestData.TEST_SWEET3)
        val UI_SWEET_LIST = listOf(UI_SWEET1, UI_SWEET2)

        val UI_FACT_1 = FactUi(TestData.TEST_FACT_1)
        val UI_FACT_2 = FactUi(TestData.TEST_FACT_2)

        val TEST_FACT_LIST = listOf(UI_FACT_1, UI_FACT_2)

        val UI_CONSUMED_SWEET1 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET)
        val UI_CONSUMED_SWEET2 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET2)
        val UI_CONSUMED_SWEET3 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET3)
        val UI_CONSUMED_SWEET4 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET4)
        val UI_CONSUMED_SWEET5 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET5)
        val UI_CONSUMED_SWEET6 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET6)
        val UI_CONSUMED_SWEET_LIST = listOf(UI_CONSUMED_SWEET1, UI_CONSUMED_SWEET2)

        val TEST_FACTMODEL_1 = FactDb(TestData.TEST_FACT_1)
        val TEST_FACTMODEL_2 = FactDb(TestData.TEST_FACT_2)

        val TEST_SWEETMODEL = SweetDb(TestData.TEST_SWEET)
        val TEST_SWEETMODEL2 = SweetDb(TestData.TEST_SWEET2)
        val TEST_LIST_SWEETMODELS = listOf(TEST_SWEETMODEL, TEST_SWEETMODEL2)

        val TEST_SWEETMODEL3 = SweetDb(TestData.TEST_SWEET3)
    }
}