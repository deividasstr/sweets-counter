package com.deividasstr.data.repositories

import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.toSweet
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
        val outcome = sweetsService.getAllSweets()
        return when (outcome) {
            is Either.Left -> outcome
            is Either.Right -> saveSweets(outcome.b)
        }
    }

    override suspend fun downloadAndSaveNewSweets(): Either<Error, Either.None> {
        val outcome = sweetsService.getNewSweets(sharedPrefs.sweetsUpdatedDate)
        return when (outcome) {
            is Either.Left -> outcome
            is Either.Right -> saveSweets(outcome.b)
        }
    }

    override suspend fun newSweet(sweet: Sweet): Either<Error, Either.None> {
        return sweetsDb.addSweet(SweetDb(sweet))
    }

    override suspend fun getSweetById(id: Long): Either<Error, Sweet> {
        return sweetsDb.getSweetById(id).map { it.toSweet() }
    }

    private suspend fun saveSweets(sweets: List<SweetDb>): Either<Error, Either.None> {
        return sweetsDb.addSweets(sweets)
    }
}