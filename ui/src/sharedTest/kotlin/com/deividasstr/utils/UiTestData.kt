package com.deividasstr.utils

import com.deividasstr.domain.common.TestData
import com.deividasstr.ui.base.models.SweetUi

class UiTestData {
    companion object {
        val UI_SWEET1 = SweetUi(TestData.TEST_SWEET)
        val UI_SWEET2 = SweetUi(TestData.TEST_SWEET2)
        val UI_SWEET_LIST = listOf(UI_SWEET1, UI_SWEET2)
    }
}