package com.deividasstr.ui.features.consumedsweetdata.models

data class PopularitySweetUi(val name: String, var consumedG: Long) {

    override fun equals(other: Any?): Boolean {
        return name == (other as PopularitySweetUi).name
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + consumedG.hashCode()
        return result
    }
}
