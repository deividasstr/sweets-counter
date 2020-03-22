package com.deividasstr.ui.features.main.backgroundwork

import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.UUID

class BackgroundWorkManager {

    companion object {
        const val SWEETS_DOWNLOAD_NAME = "SWEETS_DOWNLOAD"
        const val FACTS_DOWNLOAD_NAME = "FACTS_DOWNLOAD"
    }

    fun downloadSweetsAndSaveDownloadDate(): UUID {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadWork = OneTimeWorkRequestBuilder<DownloadAllSweetsWorker>()
            .setConstraints(constraints)
            .build()

        val saveDownloadSweetsDateWork = OneTimeWorkRequestBuilder<SaveDownloadSweetDateWorker>().build()

        val workManager = WorkManager.getInstance()

        workManager.beginUniqueWork(
            SWEETS_DOWNLOAD_NAME,
            ExistingWorkPolicy.REPLACE,
            downloadWork
        ).then(saveDownloadSweetsDateWork).enqueue()

        return downloadWork.id
    }

    fun downloadFactsAndSaveDownloadDate(): UUID {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val downloadWork = OneTimeWorkRequestBuilder<DownloadAllFactsWorker>()
            .setConstraints(constraints)
            .build()

        val saveDownloadFactsDateWork = OneTimeWorkRequestBuilder<SaveDownloadFactsDateWorker>().build()

        val workManager = WorkManager.getInstance()

        workManager.beginUniqueWork(
            FACTS_DOWNLOAD_NAME,
            ExistingWorkPolicy.REPLACE,
            downloadWork
        ).then(saveDownloadFactsDateWork).enqueue()

        return downloadWork.id
    }
}
