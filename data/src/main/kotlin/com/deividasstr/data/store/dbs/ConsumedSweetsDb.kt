package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.ConsumedSweetModel
import com.deividasstr.data.store.models.ConsumedSweetModel_
import com.deividasstr.data.store.utils.RxObjectBoxQuery
import com.deividasstr.domain.utils.DateRange
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single
import org.threeten.bp.ZoneOffset

class ConsumedSweetsDb(val db: Box<ConsumedSweetModel>)
    : ConsumedSweetsDao {

    override fun getAllConsumedSweets(): Single<List<ConsumedSweetModel>> {
        return RxObjectBoxQuery.singleList(db.query().build())
    }

    override fun addSweet(sweet: ConsumedSweetModel): Completable {
        return Completable.fromAction {
            db.put(sweet)
        }
    }

    override fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweetModel>> {
        val query = db.query().between(ConsumedSweetModel_.date,
                dateRange.start.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
                dateRange.endInclusive.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC))
                .build()
        return RxObjectBoxQuery.singleList(query)
    }
}