package com.deividasstr.data.store.dbs

import androidx.paging.DataSource
import com.deividasstr.data.DbSweet
import com.deividasstr.data.R
import com.deividasstr.data.SweetsDbQueries
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.datasource.SearchQueryDataSourceFactory
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.monads.Either
import javax.inject.Singleton

@Singleton
class SweetsDb(private val sweetsQueries: SweetsDbQueries) : SweetsDao {

    override fun query(query: String): DataSource<Int, DbSweet> {
        val wrapperQuery = "%$query%"
        return SearchQueryDataSourceFactory(
            query = wrapperQuery,
            queryProvider = sweetsQueries::searchByNamePaging,
            countQuery = sweetsQueries.countSweets(wrapperQuery),
            transacter = sweetsQueries
        ).create()
    }

    override suspend fun addSweet(sweet: DbSweet): Either<Error, Either.None> {
        sweetsQueries.insert(sweet)
        return Either.Right(Either.None())
    }

    override suspend fun addSweets(sweets: List<DbSweet>): Either<Error, Either.None> {
        sweetsQueries.transaction {
            sweets.forEach { sweetsQueries.insert(it) }
        }
        return Either.Right(Either.None())
    }

    override suspend fun getSweetById(id: Long): Either<Error, DbSweet> {
        val sweet = sweetsQueries.getById(id).executeAsOneOrNull()
        return sweet?.let { Either.Right(sweet) }
            ?: Either.Left(Error(R.string.error_item_not_found))
    }
}

fun DbSweet(sweet: Sweet): DbSweet = sweet.run {
    return DbSweet.Impl(id, name, calsPer100, fatG, carbsG, sugarG, proteinG)
}

fun Sweet(dbSweet: DbSweet): Sweet = Sweet(
    dbSweet.id,
    dbSweet.name,
    dbSweet.calsPer100,
    dbSweet.fatG,
    dbSweet.carbsG,
    dbSweet.sugarG,
    dbSweet.proteinG
)
