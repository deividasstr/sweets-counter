package com.deividasstr.data.di.modules

import android.content.Context
import com.deividasstr.data.prefs.SharedPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefsModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(context: Context): SharedPrefs {
        return SharedPrefs(context)
    }
}