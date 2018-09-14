package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Worker
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase
import com.deividasstr.ui.base.framework.base.BaseApplication
import javax.inject.Inject

class SaveDownloadSweetDateWorker : Worker() {

    @Inject lateinit var saveDownloadSweetDateUseCase: SaveDownloadSweetDateUseCase

    override fun doWork(): Result {
        (applicationContext as BaseApplication).appComponent.inject(this)
        saveDownloadSweetDateUseCase.execute().blockingAwait()
        return Result.SUCCESS
    }
}