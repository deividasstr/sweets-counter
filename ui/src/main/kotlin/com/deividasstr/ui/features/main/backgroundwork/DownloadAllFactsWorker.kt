package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase

class DownloadAllFactsWorker(
    private val downloadAllFactsUseCase: DownloadAllFactsUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    private lateinit var result: Result

    companion object {
        const val KEY_ERROR = "KEY_ERROR"
    }

    override suspend fun doWork(): Result {
        downloadAllFactsUseCase { it.either(::handleError, ::handleSuccess) }
        return result
    }

    private fun handleSuccess() {
        result = Result.success()
    }

    private fun handleError(error: Error) {
        val output = Data.Builder()
            .putBoolean(DownloadAllSweetsWorker.KEY_ERROR, true)
            .build()
        result = Result.failure(output)
    }
}
