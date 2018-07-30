package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.ConsumedSweetsDao
import com.deividasstr.data.store.models.ConsumedSweetDb
import com.deividasstr.data.store.models.ConsumedSweetDb_
import com.deividasstr.data.store.utils.RxObjectBoxQuery
import com.deividasstr.domain.utils.DateRange
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single
import org.threeten.bp.ZoneOffset
import javax.inject.Singleton

@Singleton
class ConsumedSweetsDb(val db: Box<ConsumedSweetDb>) : ConsumedSweetsDao {

    override fun getAllConsumedSweets(): Single<List<ConsumedSweetDb>> {
        return RxObjectBoxQuery.singleList(db.query().build())
    }

    override fun addSweet(sweet: ConsumedSweetDb): Completable {
        return Completable.fromAction {
            db.put(sweet)
        }
    }

    override fun getConsumedSweetsByPeriod(dateRange: DateRange): Single<List<ConsumedSweetDb>> {
        val query = db.query().between(
            ConsumedSweetDb_.date,
            dateRange.start.atStartOfDay().toEpochSecond(ZoneOffset.UTC),
            dateRange.endInclusive.plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        ).build()
        return RxObjectBoxQuery.singleList(query)
    }
}