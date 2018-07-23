package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Data
import androidx.work.Worker
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.ui.base.framework.BaseApplication
import javax.inject.Inject

class DownloadAllFactsWorker : Worker() {

    companion object {
        const val KEY_ERROR = "KEY_ERROR"
    }

    @Inject
    lateinit var downloadAllFactsUseCase: DownloadAllFactsUseCase

    override fun doWork(): Result {
        (applicationContext as BaseApplication).appComponent.inject(this)

        val throwable = downloadAllFactsUseCase.execute().blockingGet()
        if (throwable != null) {
            val output = Data.Builder()
                .putBoolean(KEY_ERROR, true)
                .build()
            outputData = output
            return Result.FAILURE
        }
        return Result.SUCCESS
    }
}