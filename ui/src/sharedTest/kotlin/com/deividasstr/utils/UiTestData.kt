package com.deividasstr.utils

import com.deividasstr.domain.common.TestData
import com.deividasstr.ui.base.models.FactUi
import com.deividasstr.ui.base.models.SweetUi

class UiTestData {
    companion object {
        val UI_SWEET1 = SweetUi(TestData.TEST_SWEET)
        val UI_SWEET2 = SweetUi(TestData.TEST_SWEET2)
        val UI_SWEET_LIST = listOf(UI_SWEET1, UI_SWEET2)

        val UI_FACT_1 = FactUi(12, "Sweets are sweet")
        val UI_FACT_2 = FactUi(1, "Sweets are bad")

        val TEST_FACT_LIST = listOf(UI_FACT_1, UI_FACT_2)
    }
}