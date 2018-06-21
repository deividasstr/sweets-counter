package com.deividasstr.domain.repositories

import io.reactivex.Completable

interface PrefsRepo {
    
    fun saveSweetsDownloadTime() : Completable
}