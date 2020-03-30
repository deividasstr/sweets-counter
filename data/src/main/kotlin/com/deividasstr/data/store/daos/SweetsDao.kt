package com.deividasstr.data.store.daos

import androidx.paging.DataSource
import com.deividasstr.data.DbSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.monads.Either

interface SweetsDao {

    suspend fun getSweetById(id: Long): Either<Error, DbSweet>

    suspend fun addSweets(sweets: List<DbSweet>): Either<Error, Either.None>

    suspend fun addSweet(sweet: DbSweet): Either<Error, Either.None>

    fun query(query: String): DataSource<Int, DbSweet>
}
