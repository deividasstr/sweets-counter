package com.deividasstr.ui.utils.di

import android.content.Context
import com.deividasstr.data.di.modules.DbModule
import com.deividasstr.data.store.models.MyObjectBox
import io.objectbox.BoxStore
import java.io.File

class TestDbModule: DbModule() {

    override fun provideDatabase(context: Context): BoxStore {
        return tempStore()
    }

    private fun tempStore(): BoxStore {
        val storeFile = File.createTempFile("object-store-test", "")
        storeFile.delete()
        return MyObjectBox.builder().directory(storeFile).build()
    }
}