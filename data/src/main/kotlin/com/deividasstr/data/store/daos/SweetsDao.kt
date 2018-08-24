package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.SweetDb
import io.reactivex.Completable
import io.reactivex.Single

interface SweetsDao {

    fun getSweetById(id: Long): Single<SweetDb>

    fun getAllSweets(): Single<List<SweetDb>>

    fun search(name: String): Single<List<SweetDb>>

    fun addSweets(sweets: List<SweetDb>): Completable

    fun addSweet(sweet: SweetDb): Completable

    fun getSweetsByIds(ids: LongArray): Single<List<SweetDb>>

    fun removeAll(): Completable
}