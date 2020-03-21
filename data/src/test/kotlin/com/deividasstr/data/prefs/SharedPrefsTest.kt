package com.deividasstr.data.prefs

import android.content.Context
import android.content.SharedPreferences
import com.deividasstr.testutils.UnitTest

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willReturn
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class SharedPrefsTest : UnitTest() {

    companion object {
        const val DATE_LONG = 987654321L
    }

    private lateinit var prefs: SharedPrefs
    private lateinit var sharedPreferencesEditor: SharedPrefsEditorFakeImpl

    @Mock
    private lateinit var ctx: Context

    @Mock
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        sharedPreferencesEditor = SharedPrefsEditorFakeImpl()
        given {
            ctx.getSharedPreferences(SharedPrefs.PREFS_NAME, Context.MODE_PRIVATE)
        } willReturn { sharedPreferences }

        given { sharedPreferences.edit() } willReturn { sharedPreferencesEditor }
        prefs = SharedPrefs(ctx)
    }

    @Test
    fun shouldGetFactsUpdatedDate() {
        given { sharedPreferences.getLong(SharedPrefs.PREF_FACT_DATE, 0) }
            .willReturn(DATE_LONG)

        assertEquals(DATE_LONG, prefs.factsUpdatedDate)
    }

    @Test
    fun shouldGetSweetsUpdatedDate() {
        given { sharedPreferences.getLong(SharedPrefs.PREF_SWEET_DATE, 0) }
            .willReturn(DATE_LONG)

        assertEquals(DATE_LONG, prefs.sweetsUpdatedDate)
    }

    @Test
    fun shouldSetFactsUpdatedDate() {
        prefs.factsUpdatedDate = DATE_LONG

        assertEquals(DATE_LONG, sharedPreferencesEditor.long)
    }

    @Test
    fun shouldSetSweetsUpdatedDate() {
        prefs.sweetsUpdatedDate = DATE_LONG

        assertEquals(DATE_LONG, sharedPreferencesEditor.long)
    }
}