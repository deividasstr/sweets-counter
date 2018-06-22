package com.deividasstr.ui.base.framework

import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.StringRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Context.alert(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.alert(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

val Context.networkAvailable: Boolean get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected ?: false

fun View.openKeyboard() {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}

/*
fun View.closeKeyboard() {
    val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    keyboard.(InputMethodManager.SHOW_FORCED, 0)
}*/
