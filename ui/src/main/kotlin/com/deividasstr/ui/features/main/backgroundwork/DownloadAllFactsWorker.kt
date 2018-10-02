package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.ui.base.framework.base.BaseApplication
import javax.inject.Inject

class DownloadAllFactsWorker(context: Context, val configuration: WorkerParameters) :
    Worker(context, configuration) {

    companion object {
        const val KEY_ERROR = "KEY_ERROR"
    }

    @Inject
    lateinit var downloadAllFactsUseCase: DownloadAllFactsUseCase

    override fun doWork(): Result {
        (applicationContext as BaseApplication).appComponent.inject(this)

        val throwable = downloadAllFactsUseCase.execute().blockingGet()
        if (throwable != null) {
            val output = Data.Builder().putBoolean(KEY_ERROR, true).build()
            outputData = output
            return Result.FAILURE
        }
        return Result.SUCCESS
    }
}