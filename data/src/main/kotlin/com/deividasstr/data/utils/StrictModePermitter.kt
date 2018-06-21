package com.deividasstr.data.utils

import android.os.StrictMode
import com.deividasstr.data.BuildConfig

/**
 * https://medium.com/@elye.project/android-strict-mode-selective-code-suppression-37ee0d999f6b
 * Some background reads which are detected by StrictMode in trivial, harmless code of third-party
 * libs, like SharedPreferences context.getSharedPreferences and creation of ObjectBox main store
 * in dagger.
 *
 * permitDiskReads wraps any of those calls. It disables StrictMode disk reads, runs the call,
 * resets StrictMode settings and returns the result without triggering StrictMode.
 */
object StrictModePermitter {
    fun <T>permitDiskReads(func: () -> T) : T {
        return if (BuildConfig.DEBUG) {
            val oldThreadPolicy = StrictMode.getThreadPolicy()
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder(oldThreadPolicy)
                    .permitDiskReads().build())
            val anyValue = func()
            StrictMode.setThreadPolicy(oldThreadPolicy)

            anyValue
        } else {
            func()
        }
    }
}