package com.deividasstr.domain.repositories

import com.deividasstr.domain.entities.Sweet
import io.reactivex.Single

interface SweetsRepo {

    fun getAllSweets(): Single<List<Sweet>>

    fun getSweetById(id: Int): Single<Sweet>

    fun search(name: String): Single<List<Sweet>>
}