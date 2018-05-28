package com.deividasstr.data.prefs

import android.content.SharedPreferences

class SharedPrefsEditorFakeImpl : SharedPreferences.Editor {

    var long: Long = 0

    override fun clear(): SharedPreferences.Editor {
        return this
    }

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
        return this
    }

    override fun remove(key: String?): SharedPreferences.Editor {
        return this
    }

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
        return this
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
        return this
    }

    override fun commit(): Boolean {
        return true
    }

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
        return this
    }

    override fun apply() {
    }

    override fun putString(key: String?, value: String?): SharedPreferences.Editor {
        return this
    }

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
        long = value
        return this
    }
}