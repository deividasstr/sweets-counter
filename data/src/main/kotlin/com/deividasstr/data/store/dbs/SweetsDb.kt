package com.deividasstr.data.store.dbs

import com.deividasstr.data.R
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.SweetDb_
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either
import io.objectbox.Box
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import javax.inject.Singleton

@Singleton
class SweetsDb(private val db: Box<SweetDb>) : SweetsDao {

    fun query(query: String): Query<SweetDb> {
        return db.query {
            contains(SweetDb_.name, query)
            order(SweetDb_.name)
        }
    }

    override suspend fun addSweet(sweet: SweetDb): Either<Error, Either.None> {
        db.put(sweet)
        return Either.Right(Either.None())
    }

    override suspend fun getAllSweets(): Either<Error, List<SweetDb>> {
        val query = db.query().order(SweetDb_.name).build()
        return Either.Right(query.find())
    }

    override suspend fun addSweets(sweets: List<SweetDb>): Either<Error, Either.None> {
        db.put(sweets)
        return Either.Right(Either.None())
    }

    override suspend fun getSweetById(id: Long): Either<Error, SweetDb> {
        val sweet = db.query().equal(SweetDb_.id, id).build().findFirst()
        return sweet?.let { Either.Right(sweet) } ?: Either.Left(Error(R.string.error_item_not_found))
    }
}