package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.SweetDb_
import com.deividasstr.data.store.utils.RxObjectBoxQuery
import com.deividasstr.domain.entities.Sweet
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class SweetsDb(val db: Box<SweetDb>) : SweetsDao {

    override fun getSweetsByIds(ids: LongArray): Single<List<SweetDb>> {
        val query = db.query().order(SweetDb_.name).`in`(SweetDb_.id, ids) .build()
        return RxObjectBoxQuery.singleList(query)
    }

    override fun getLastUpdateTimeStamp(): Single<Long> {
        val query = db.query().orderDesc(SweetDb_.addedTimestamp).build()
        return RxObjectBoxQuery.singleItem(query).map { it.addedTimestamp }
    }

    override fun addSweet(sweet: SweetDb): Completable {
        return Completable.fromAction {
            db.put(sweet)
        }
    }

    override fun getAllSweets(): Single<List<SweetDb>> {
        val query = db.query().order(SweetDb_.name).build()
        return RxObjectBoxQuery.singleList(query)
    }

    override fun addSweets(sweets: List<SweetDb>): Completable {
        return Completable.fromAction {
            db.put(sweets)
        }
    }

    override fun getSweetById(id: Long): Single<SweetDb> {
        val query = db.query().equal(SweetDb_.id, id).build()
        return RxObjectBoxQuery.singleItem(query)
    }

    override fun search(name: String): Single<List<SweetDb>> {
        return if (name.isEmpty()) {
            getAllSweets()
        } else {
            val query = db.query().equal(SweetDb_.name, name).build()
            RxObjectBoxQuery.singleList(query)
        }
    }
}