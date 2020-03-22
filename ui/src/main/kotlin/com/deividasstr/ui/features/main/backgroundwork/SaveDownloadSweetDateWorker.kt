package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase

class SaveDownloadSweetDateWorker(
    private val saveDownloadSweetDateUseCase: SaveDownloadSweetDateUseCase,
    context: Context,
    configuration: WorkerParameters
) :
    CoroutineWorker(context, configuration) {

    private lateinit var result: Result

    override suspend fun doWork(): Result {
        saveDownloadSweetDateUseCase { it.either(::handleError, ::handleSuccess) }
        return result
    }

    private fun handleSuccess() {
        result = Result.success()
    }

    private fun handleError(error: Error) {
        result = Result.failure()
    }
}
