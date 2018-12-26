package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase

class SaveDownloadSweetDateWorker(
    private val saveDownloadSweetDateUseCase: SaveDownloadSweetDateUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    override suspend fun doWork(): Result {
        saveDownloadSweetDateUseCase.execute().blockingAwait()
        return Result.success()
    }
}