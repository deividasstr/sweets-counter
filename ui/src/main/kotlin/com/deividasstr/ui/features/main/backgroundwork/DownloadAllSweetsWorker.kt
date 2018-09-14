package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Data
import androidx.work.Worker
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase
import com.deividasstr.ui.base.framework.base.BaseApplication
import javax.inject.Inject

class DownloadAllSweetsWorker : Worker() {

    companion object { const val KEY_ERROR = "KEY_ERROR" }

    @Inject
    lateinit var downloadAllSweetsUseCase: DownloadAllSweetsUseCase

    override fun doWork(): Result {
        (applicationContext as BaseApplication).appComponent.inject(this)

        val throwable = downloadAllSweetsUseCase.execute().blockingGet()
        if (throwable != null) {
            val output = Data.Builder()
                .putBoolean(KEY_ERROR, true)
                .build()
            outputData = output
            return Result.RETRY
        }
        return Result.SUCCESS
    }
}