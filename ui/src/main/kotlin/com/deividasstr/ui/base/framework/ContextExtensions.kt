package com.deividasstr.ui.base.framework

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.alert(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.alert(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}