package com.deividasstr.ui.features.main.backgroundwork

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.deividasstr.domain.usecases.DownloadAllFactsUseCase
import com.deividasstr.domain.usecases.DownloadAllSweetsUseCase
import com.deividasstr.domain.usecases.SaveDownloadFactDateUseCase
import com.deividasstr.domain.usecases.SaveDownloadSweetDateUseCase
import javax.inject.Inject

class WorkersFactory @Inject constructor(
    private val downloadAllFactsUseCase: DownloadAllFactsUseCase,
    private val downloadAllSweetsUseCase: DownloadAllSweetsUseCase,
    private val saveDownloadFactDateUseCase: SaveDownloadFactDateUseCase,
    private val saveDownloadSweetDateUseCase: SaveDownloadSweetDateUseCase
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            DownloadAllFactsWorker::class.java.name -> getDownloadAllFactsWorker(
                appContext,
                workerParameters)

            DownloadAllSweetsWorker::class.java.name -> getDownloadAllSweetsWorker(
                appContext,
                workerParameters)

            SaveDownloadFactsDateWorker::class.java.name -> getSaveDownloadFactDateWorker(
                appContext,
                workerParameters)

            SaveDownloadSweetDateWorker::class.java.name -> getSaveDownloadSweetDateWorker(
                appContext,
                workerParameters)

            else -> throw IllegalArgumentException("Unexpected worker $workerClassName")
        }
    }

    private fun getDownloadAllFactsWorker(
        appContext: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return DownloadAllFactsWorker(downloadAllFactsUseCase, appContext, workerParameters)
    }

    private fun getDownloadAllSweetsWorker(
        appContext: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return DownloadAllSweetsWorker(downloadAllSweetsUseCase, appContext, workerParameters)
    }

    private fun getSaveDownloadSweetDateWorker(
        appContext: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return SaveDownloadSweetDateWorker(
            saveDownloadSweetDateUseCase,
            appContext,
            workerParameters)
    }

    private fun getSaveDownloadFactDateWorker(
        appContext: Context,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return SaveDownloadFactsDateWorker(
            saveDownloadFactDateUseCase,
            appContext,
            workerParameters)
    }
}