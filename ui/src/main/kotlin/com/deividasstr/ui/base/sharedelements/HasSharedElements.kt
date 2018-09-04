package com.deividasstr.ui.base.sharedelements

import android.view.View

/**
 * @author Alexey Pushkarev on 05.06.2018.
 */
interface HasSharedElements {

    fun getSharedElements(): Map<String, View>

    fun hasReorderingAllowed(): Boolean
}