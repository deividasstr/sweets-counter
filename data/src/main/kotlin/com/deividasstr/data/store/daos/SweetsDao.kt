package com.deividasstr.data.store.daos

import com.deividasstr.data.store.models.SweetModel
import io.reactivex.Completable
import io.reactivex.Single

interface SweetsDao {

    fun getSweetById(id: Long): Single<SweetModel>

    fun getAllSweets(): Single<List<SweetModel>>

    fun search(name: String): Single<List<SweetModel>>

    fun addSweets(sweets: List<SweetModel>): Completable

    fun addSweet(sweet: SweetModel): Completable

    fun getLastUpdateTimeStamp(): Single<Long>
}