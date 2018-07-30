package com.deividasstr.ui.features.main

import androidx.work.WorkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.utils.StringResException
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.features.main.backgroundwork.BackgroundWorkManager
import com.deividasstr.ui.features.main.backgroundwork.DownloadAllSweetsWorker
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val backgroundWorkManager: BackgroundWorkManager
) : BaseViewModel() {

    fun tryDownloadSweets() {
        if (sharedPrefs.sweetsUpdatedDate == 0L) {
            val workManager = WorkManager.getInstance()

            val workId = backgroundWorkManager.downloadSweetsAndSaveDownloadDate()

            val workStatus = workManager.getStatusById(workId)

            _errorMessage.addSource(workStatus) {
                if (it.outputData.getBoolean(DownloadAllSweetsWorker.KEY_ERROR, false)) {
                    setError(StringResException(R.string.error_network_server))
                }
            }
        }
    }

    fun tryDownloadFacts() {
        if (sharedPrefs.factsUpdatedDate == 0L) {
            val workManager = WorkManager.getInstance()

            val workId = backgroundWorkManager.downloadFactsAndSaveDownloadDate()

            val workStatus = workManager.getStatusById(workId)

            _errorMessage.addSource(workStatus) {
                if (it.outputData.getBoolean(DownloadAllSweetsWorker.KEY_ERROR, false)) {
                    setError(StringResException(R.string.error_network_server))
                }
            }
        }
    }
}