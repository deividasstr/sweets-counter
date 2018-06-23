package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.Sweet
import io.reactivex.Completable
import io.reactivex.Single

interface SweetsRepo {

    fun getAllSweets(): Single<List<Sweet>>

    fun getSweetById(id: Long): Single<Sweet>

    fun getSweetsByIds(ids: LongArray): Single<List<Sweet>>

    fun search(name: String): Single<List<Sweet>>

    fun newSweet(sweet: Sweet): Completable

    fun downloadAndSaveAllSweets(): Completable

    fun downloadAndSaveNewSweets(): Completable
}