package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase

class DownloadAllFactsWorker(
    private val downloadAllFactsUseCase: DownloadAllFactsUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    override suspend fun doWork(): Result {
        val throwable = downloadAllFactsUseCase.execute().blockingGet()
        if (throwable != null) {
            val output = Data.Builder().putBoolean(KEY_ERROR, true).build()
            return Result.failure(output)
        }
        return Result.success()
    }

    companion object {
        const val KEY_ERROR = "KEY_ERROR"
    }
}