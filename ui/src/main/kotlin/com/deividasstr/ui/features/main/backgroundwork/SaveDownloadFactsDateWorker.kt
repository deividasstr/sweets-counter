package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Worker
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase
import com.deividasstr.ui.base.framework.base.BaseApplication
import javax.inject.Inject

class SaveDownloadFactsDateWorker : Worker() {

    @Inject lateinit var saveDownloadDateUseCase: SaveDownloadFactDateUseCase

    override fun doWork(): Result {
        (applicationContext as BaseApplication).appComponent.inject(this)
        saveDownloadDateUseCase.execute().blockingAwait()
        return Result.SUCCESS
    }
}