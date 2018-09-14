package com.deividasstr.data.repositories

import com.deividasstr.data.networking.services.SweetsService
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.store.daos.SweetsDao
import com.deividasstr.data.store.models.SweetDb
import com.deividasstr.data.store.models.toSweet
import com.deividasstr.data.store.models.toSweets
import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class SweetsRepoImpl(
    private val sweetsDb: SweetsDao,
    private val sweetsService: SweetsService,
    private val sharedPrefs: SharedPrefs
) : SweetsRepo {

    override fun downloadAndSaveAllSweets(): Completable {
        return sweetsService.getAllSweets().flatMapCompletable { saveSweets(it) }
    }

    override fun downloadAndSaveNewSweets(): Completable {
        return sweetsService.getNewSweets(sharedPrefs.sweetsUpdatedDate)
            .flatMapCompletable { saveSweets(it) }
    }

    override fun newSweet(sweet: Sweet): Completable {
        return sweetsDb.addSweet(SweetDb(sweet))
    }

    override fun getAllSweets(): Single<List<Sweet>> {
        return sweetsDb.getAllSweets().map { it.toSweets() }
    }

    override fun getSweetById(id: Long): Single<Sweet> {
        return sweetsDb.getSweetById(id).map { it.toSweet() }
    }

    override fun search(name: String): Single<List<Sweet>> {
        return sweetsDb.search(name).map { it.toSweets() }
    }

    private fun saveSweets(sweets: List<SweetDb>): Completable {
        return sweetsDb.addSweets(sweets)
    }
}