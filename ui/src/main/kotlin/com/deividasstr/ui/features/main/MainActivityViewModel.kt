package com.deividasstr.ui.features.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.features.main.workers.DownloadAllSweetsWorker
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val sharedPrefs: SharedPrefs) :
    BaseViewModel() {

    private var _errorMessage: LiveData<SingleEvent<Throwable>> = MutableLiveData()

    val errorMessage: LiveData<SingleEvent<Throwable>>
        get() = _errorMessage

    fun tryDownloadSweets() {
        if (sharedPrefs.sweetsUpdatedDate == 0L) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<DownloadAllSweetsWorker>()
                .setConstraints(constraints)
                .build()

            val workManager = WorkManager.getInstance()
            workManager.enqueue(request)
            _errorMessage = Transformations.map(workManager.getStatusById(request.id), {
                it.outputData.keyValueMap[DownloadAllSweetsWorker.KEY_ERROR]?.let {
                    SingleEvent(Throwable(it as String?))
                }
            })
        }
    }
}