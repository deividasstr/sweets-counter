package com.deividasstr.data.repositories

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.toConsumedSweets
import com.deividasstr.domain.models.ConsumedSweet
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ConsumedSweetsRepoImpl(
    private val consumedSweetsDb: ConsumedSweetsDao
) : ConsumedSweetsRepo {

    override fun addSweet(sweet: ConsumedSweet): Completable {
        return consumedSweetsDb.addSweet(ConsumedSweetDb(sweet))
    }

    override fun getAllConsumedSweets(): Single<List<ConsumedSweet>> {
        return consumedSweetsDb.getAllConsumedSweets().map { it.toConsumedSweets() }
    }
}