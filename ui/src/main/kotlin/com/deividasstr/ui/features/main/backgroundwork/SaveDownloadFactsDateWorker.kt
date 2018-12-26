package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase

class SaveDownloadFactsDateWorker(
    private val saveDownloadDateUseCase: SaveDownloadFactDateUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    override suspend fun doWork(): Result {
        saveDownloadDateUseCase.execute().blockingAwait()
        return Result.success()
    }
}