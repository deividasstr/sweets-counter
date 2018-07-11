package com.deividasstr.ui.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.WorkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.features.main.backgroundwork.BackgroundWorkManager
import com.deividasstr.ui.features.main.backgroundwork.DownloadAllSweetsWorker
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val backgroundWorkManager: BackgroundWorkManager
) : BaseViewModel() {

    private var _errorMessage: LiveData<SingleEvent<Throwable>> = MutableLiveData()

    val errorMessage: LiveData<SingleEvent<Throwable>>
        get() = _errorMessage

    fun tryDownloadSweets() {
        if (sharedPrefs.sweetsUpdatedDate == 0L) {
            val workManager = WorkManager.getInstance()

            val workId = backgroundWorkManager.downloadSweetsAndSaveDownloadDate()

            val workStatus = workManager?.getStatusById(workId) ?: return

            _errorMessage =
                Transformations.map(workStatus) {
                    if (it.outputData.getBoolean(DownloadAllSweetsWorker.KEY_ERROR, false)) {
                        SingleEvent(Throwable(""))
                    } else {
                        null
                    }
                }
        }
    }
}