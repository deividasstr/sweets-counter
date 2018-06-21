package com.deividasstr.data.prefs

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharedPrefs(private val ctx: Context) {

    companion object {
        const val PREFS_NAME = "PREFS_NAME"
        const val PREF_FACT_DATE = "PREF_FACT_DATE"
        const val PREF_SWEET_DATE = "PREF_SWEET_DATE"
    }

    private val prefs: SharedPreferences by lazy {
        ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var factsUpdatedDate: Long
        get() = prefs.getLong(PREF_FACT_DATE, 0)
        set(value) {
            prefs.edit().putLong(PREF_FACT_DATE, value).apply()
        }

    var sweetsUpdatedDate: Long
        get() = prefs.getLong(PREF_SWEET_DATE, 0)
        set(value) = prefs.edit().putLong(PREF_SWEET_DATE, value).apply()
}