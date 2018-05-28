package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.SweetModel
import com.deividasstr.data.store.models.SweetModel_
import com.deividasstr.data.store.utils.RxObjectBoxQuery
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single

class SweetsDb(val db: Box<SweetModel>) : SweetsDao {

    override fun getLastUpdateTimeStamp(): Single<Long> {
        val query = db.query().orderDesc(SweetModel_.addedTimestamp).build()
        return RxObjectBoxQuery.singleItem(query).map { it.addedTimestamp }
    }

    override fun addSweet(sweet: SweetModel): Completable {
        return Completable.fromAction {
            db.put(sweet)
        }
    }

    override fun getAllSweets(): Single<List<SweetModel>> {
        val query = db.query().order(SweetModel_.name).build()
        return RxObjectBoxQuery.singleList(query)
    }

    override fun addSweets(sweets: List<SweetModel>): Completable {
        return Completable.fromAction {
            db.put(sweets)
        }
    }

    override fun getSweetById(id: Long): Single<SweetModel> {
        val query = db.query().equal(SweetModel_.id, id).build()
        return RxObjectBoxQuery.singleItem(query)
    }

    override fun search(name: String): Single<List<SweetModel>> {
        return if (name.isEmpty()) {
            getAllSweets()
        } else {
            val query = db.query().equal(SweetModel_.name, name).build()
            RxObjectBoxQuery.singleList(query)
        }
    }
}