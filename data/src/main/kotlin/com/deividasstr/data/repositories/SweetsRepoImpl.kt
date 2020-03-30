package com.deividasstr.data.repositories

import com.deividasstr.data.DbSweet
import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.dbs.DbSweet
import com.deividasstr.data.store.dbs.Sweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Sweet
import com.deividasstr.domain.monads.Either
import com.deividasstr.domain.repositories.SweetsRepo
import javax.inject.Singleton

@Singleton
class SweetsRepoImpl(
    private val sweetsDb: SweetsDao,
    private val sweetsService: SweetsService,
    private val sharedPrefs: SharedPrefs
) : SweetsRepo {

    override suspend fun downloadAndSaveAllSweets(): Either<Error, Either.None> {
        return when (val result = sweetsService.getAllSweets()) {
            is Either.Left -> result
            is Either.Right -> saveSweets(result.right)
        }
    }

    override suspend fun downloadAndSaveNewSweets(): Either<Error, Either.None> {
        return when (val result = sweetsService.getNewSweets(sharedPrefs.sweetsUpdatedDate)) {
            is Either.Left -> result
            is Either.Right -> saveSweets(result.right)
        }
    }

    override suspend fun newSweet(sweet: Sweet): Either<Error, Either.None> {
        return sweetsDb.addSweet(DbSweet(sweet))
    }

    override suspend fun getSweetById(id: Long): Either<Error, Sweet> {
        return sweetsDb.getSweetById(id).map { Sweet(it) }
    }

    private suspend fun saveSweets(sweets: List<DbSweet>): Either<Error, Either.None> {
        return sweetsDb.addSweets(sweets)
    }
}
