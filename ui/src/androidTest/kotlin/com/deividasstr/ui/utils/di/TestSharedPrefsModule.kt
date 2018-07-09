package com.deividasstr.ui.utils.di

import android.content.Context
import com.deividasstr.data.di.modules.SharedPrefsModule
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.repositories.PrefsRepo
import com.nhaarman.mockito_kotlin.mock

class TestSharedPrefsModule : SharedPrefsModule() {

    /*@Singleton
    @Provides
    fun provideSharedPrefs(context: Context): SharedPrefs {
        val mock = mock(SharedPrefs::class.java)
        given { mock.sweetsUpdatedDate }.willReturn { 1 }
        return mock
    }

    @Singleton
    @Provides
    fun providePrefsRepo(context: Context): PrefsRepo {
        return mock(PrefsRepo::class.java)
    }*/

    override fun provideSharedPrefs(context: Context): SharedPrefs {
        return mock()
    }

    override fun providePrefsRepo(context: Context): PrefsRepo {
        return mock()
    }
}