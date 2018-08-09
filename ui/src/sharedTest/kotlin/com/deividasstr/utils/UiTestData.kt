package com.deividasstr.utils

import com.deividasstr.domain.common.TestData
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.FactUi
import com.deividasstr.ui.base.models.SweetUi

class UiTestData {
    companion object {
        val UI_SWEET1 = SweetUi(TestData.TEST_SWEET)
        val UI_SWEET2 = SweetUi(TestData.TEST_SWEET2)
        val UI_SWEET3 = SweetUi(TestData.TEST_SWEET3)
        val UI_SWEET_LIST = listOf(UI_SWEET1, UI_SWEET2)

        val UI_FACT_1 = FactUi(12, "Sweets are sweet")
        val UI_FACT_2 = FactUi(1, "Sweets are bad")

        val TEST_FACT_LIST = listOf(UI_FACT_1, UI_FACT_2)

        val UI_CONSUMED_SWEET1 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET)
        val UI_CONSUMED_SWEET2 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET2)
        val UI_CONSUMED_SWEET3 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET3)
        val UI_CONSUMED_SWEET4 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET4)
        val UI_CONSUMED_SWEET5 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET5)
        val UI_CONSUMED_SWEET6 = ConsumedSweetUi(TestData.TEST_CONSUMED_SWEET6)
        val UI_CONSUMED_SWEET_LIST = listOf(UI_CONSUMED_SWEET1, UI_CONSUMED_SWEET2)
    }
}