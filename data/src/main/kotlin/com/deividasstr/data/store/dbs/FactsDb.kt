package com.deividasstr.data.store.dbs

import com.deividasstr.data.R
import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.FactDb_
import com.deividasstr.data.utils.StringResException
import io.objectbox.Box
import io.objectbox.query.LazyList
import io.reactivex.Completable
import io.reactivex.Single
import java.util.Random
import javax.inject.Singleton

@Singleton
class FactsDb(val db: Box<FactDb>) : FactsDao {

    override fun addFacts(facts: List<FactDb>): Completable {
        return Completable.fromAction {
            db.put(facts)
        }
    }

    override fun getRandomFact(currentFactId: Long): Single<FactDb> {
        return Single.create {
            val factLazyList: LazyList<FactDb> = when {
                currentFactId < 0 -> {
                    db.query().build().findLazy()
                }
                else -> {
                    db.query().notEqual(FactDb_.id, currentFactId).build().findLazy()
                }
            }

            if (factLazyList.isEmpty()) {
                it.onError(StringResException(R.string.error_no_facts_available))
                return@create
            }

            val random = Random().nextInt(factLazyList.size)
            it.onSuccess((factLazyList[random]))
        }
    }
}