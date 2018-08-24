package com.deividasstr.data.repositories

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.toConsumedSweets
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.DateRange
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class ConsumedSweetsRepoImpl(
    private val consumedSweetsDb: ConsumedSweetsDao,
    private val sweetsDb: SweetsDao
) : ConsumedSweetsRepo {

    override fun getTotalCalsConsumed(): Single<Long> {
        return consumedSweetsDb.getAllConsumedSweets()
            .map { consumedSweets ->
                val ids = consumedSweets.toSet().map { it.sweetId }.toLongArray()
                var calsCount = 0L
                if (ids.isNotEmpty()) {
                    val uniqueSweets = sweetsDb.getSweetsByIds(ids).blockingGet()

                    consumedSweets.forEach { consumedSweet ->
                        val calsPer100g =
                            uniqueSweets.find { it.id == consumedSweet.sweetId }?.calsPer100!!
                        calsCount += (consumedSweet.g * calsPer100g / 100)
                    }
                }
                calsCount
            }
    }

    override fun addSweet(sweet: ConsumedSweet): Completable {
        return consumedSweetsDb.addSweet(ConsumedSweetDb(sweet))
    }

    override fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweet>> {
        return consumedSweetsDb.getConsumedSweetsByPeriod(dateRange).map { it.toConsumedSweets() }
    }

    override fun getAllConsumedSweets(): Single<List<ConsumedSweet>> {
        return consumedSweetsDb.getAllConsumedSweets().map { it.toConsumedSweets() }
    }
}