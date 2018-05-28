package com.deividasstr.data.repositories

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.ConsumedSweetModel
import com.deividasstr.data.store.models.toConsumedSweets
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import com.deividasstr.domain.utils.DateRange
import io.reactivex.Completable
import io.reactivex.Single
import kotlin.math.roundToInt

class ConsumedSweetsRepoImpl(
    val consumedSweetsDb: ConsumedSweetsDao,
    val sweetsDb: SweetsDao
) : ConsumedSweetsRepo {

    override fun getTotalCalsConsumed(): Single<Int> {
        return Single.create { emitter ->
            consumedSweetsDb.getAllConsumedSweets().subscribe {
                list: List<ConsumedSweetModel> ->
                var calCount = 0
                list.forEach {
                    getCalsPer100GBySweetId(it.sweetId).subscribe { sweet: Sweet ->
                        calCount += (it.g * sweet.calsPer100 / 100).roundToInt()
                    }
                }
                emitter.onSuccess(calCount)
            }
        }
    }

    override fun addSweet(sweet: ConsumedSweet): Completable {
        return consumedSweetsDb.addSweet(ConsumedSweetModel(sweet))
    }

    override fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweet>> {
        return consumedSweetsDb.getConsumedSweetsByPeriod(dateRange).map { it.toConsumedSweets() }
    }

    private fun getCalsPer100GBySweetId(id: Int): Single<Sweet> {
        return sweetsDb.getSweetById(id.toLong()).map { it.toSweet() }
    }
}