package com.deividasstr.domain.usecases

import com.deividasstr.domain.framework.CompletableUseCase
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Completable

class DownloadAllSweetsUseCase(private val repo: SweetsRepo) : CompletableUseCase {
    override fun execute(): Completable {
        return repo.downloadAndSaveAllSweets()
    }
}