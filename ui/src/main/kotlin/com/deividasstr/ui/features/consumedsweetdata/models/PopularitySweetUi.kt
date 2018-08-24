package com.deividasstr.ui.features.consumedsweetdata.models

import com.deividasstr.ui.base.models.SweetUi

data class PopularitySweetUi(val sweet: SweetUi, var consumedG: Long) {

    override fun equals(other: Any?): Boolean {
        return sweet == (other as PopularitySweetUi).sweet
    }

    override fun hashCode(): Int {
        var result = sweet.hashCode()
        result = 31 * result + consumedG.hashCode()
        return result
    }
}