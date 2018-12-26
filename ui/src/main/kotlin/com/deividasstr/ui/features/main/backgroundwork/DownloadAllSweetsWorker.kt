package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase

class DownloadAllSweetsWorker(
    private val downloadAllSweetsUseCase: DownloadAllSweetsUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    companion object {
        const val KEY_ERROR = "KEY_ERROR"
    }

    override suspend fun doWork(): Result {
        val throwable = downloadAllSweetsUseCase.execute(). .blockingGet()
        if (throwable != null) {
            val output = Data.Builder()
                .putBoolean(KEY_ERROR, true)
                .build()
            return Result.failure(output)
        }
        return Result.success()
    }
}