package com.deividasstr.data.store.dbs

import com.deividasstr.data.store.daos.FactsDao
import com.deividasstr.data.store.models.FactDb
import com.deividasstr.data.store.models.FactDb_
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import io.objectbox.Box
import io.objectbox.query.LazyList
import java.util.Random
import javax.inject.Singleton

@Singleton
class FactsDb(private val db: Box<FactDb>) : FactsDao {

    override suspend fun addFacts(facts: List<FactDb>): Either<Error, Either.None> {
        db.put(facts)
        return Either.Right(Either.None())
    }

    override suspend fun getRandomFact(currentFactId: Long): Either<Error, FactDb> {
        val factLazyList: LazyList<FactDb> = when {
            currentFactId < 0 -> {
                db.query().build().findLazy()
            }
            else -> {
                db.query().notEqual(FactDb_.id, currentFactId).build().findLazy()
            }
        }

        val random = Random().nextInt(factLazyList.size)
        return Either.Right(factLazyList[random])
    }
}