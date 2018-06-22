package com.deividasstr.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.features.main.workers.DownloadAllSweetsWorker
import com.deividasstr.ui.features.main.workers.SaveDownloadDateWorker
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val sharedPrefs: SharedPrefs) :
    BaseViewModel() {

    companion object {
        const val SWEETS_DOWNLOAD_NAME = "SWEETS_DOWNLOAD"
    }

    private var _errorMessage: LiveData<SingleEvent<Throwable>> = MutableLiveData()

    val errorMessage: LiveData<SingleEvent<Throwable>>
        get() = _errorMessage

    fun tryDownloadSweets() {
        if (sharedPrefs.sweetsUpdatedDate == 0L) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val downloadWork = OneTimeWorkRequestBuilder<DownloadAllSweetsWorker>()
                .setConstraints(constraints)
                .build()

            val saveDownloadDateWork = OneTimeWorkRequestBuilder<SaveDownloadDateWorker>().build()

            val workManager = WorkManager.getInstance()

            workManager.beginUniqueWork(
                SWEETS_DOWNLOAD_NAME,
                ExistingWorkPolicy.REPLACE,
                downloadWork
            ).then(saveDownloadDateWork).enqueue()

            _errorMessage = Transformations.map(workManager.getStatusById(downloadWork.id)) {
                if (it.outputData.getBoolean(DownloadAllSweetsWorker.KEY_ERROR, false)) {
                    SingleEvent(Throwable(""))
                } else {
                    null
                }
            }
        }
    }
}