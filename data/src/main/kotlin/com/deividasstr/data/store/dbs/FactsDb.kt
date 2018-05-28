package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.models.FactModel
import com.deividasstr.data.store.models.FactModel_
import com.deividasstr.data.store.utils.RxObjectBoxQuery
import io.objectbox.Box
import io.objectbox.query.LazyList
import io.reactivex.Completable
import io.reactivex.Single
import java.util.Random

class FactsDb(val db: Box<FactModel>) : FactsDao {

    override fun getLastUpdateTimeStamp(): Single<Long> {
        val query = db.query().orderDesc(FactModel_.addedTimestamp).build()
        return RxObjectBoxQuery.singleItem(query).map { it.addedTimestamp }
    }

    override fun addFacts(facts: List<FactModel>): Completable {
        return Completable.fromAction {
            db.put(facts)
        }
    }

    override fun getRandomFact(currentFactId: Long): Single<FactModel> {
        return Single.create {
            val factLazyList: LazyList<FactModel> = when {
                currentFactId < 0 -> {
                    db.query().build().findLazy()
                }
                else -> {
                    db.query().notEqual(FactModel_.id, currentFactId).build().findLazy()
                }
            }

            if (factLazyList.isEmpty()) {
                it.onError(NullPointerException("No facts available"))
                return@create
            }

            val random = Random().nextInt(factLazyList.size)
            it.onSuccess((factLazyList[random]))
        }
    }
}