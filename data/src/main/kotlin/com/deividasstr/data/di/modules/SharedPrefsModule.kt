package com.deividasstr.data.di.modules

import android.content.Context
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.repositories.PrefsRepo
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

    @Singleton
    @Provides
    fun providePrefsRepo(context: Context): PrefsRepo {
        return SharedPrefs(context)
    }
}
