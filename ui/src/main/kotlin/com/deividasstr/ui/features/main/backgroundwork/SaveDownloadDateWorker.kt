package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Worker
import com.deividasstr.domain.usecases.SaveDownloadDateUseCase
import com.deividasstr.ui.base.SweetsApplication
import javax.inject.Inject

class SaveDownloadDateWorker : Worker() {

    @Inject lateinit var saveDownloadDateUseCase: SaveDownloadDateUseCase

    override fun doWork(): Result {
        (applicationContext as SweetsApplication).appComponent.inject(this)
        saveDownloadDateUseCase.execute().blockingAwait()
        return Result.SUCCESS
    }
}