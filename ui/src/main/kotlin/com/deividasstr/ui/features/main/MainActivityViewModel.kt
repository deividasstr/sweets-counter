package com.deividasstr.ui.features.main

import androidx.work.WorkManager
import com.deividasstr.data.networking.manager.NetworkManager
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.features.main.backgroundwork.BackgroundWorkManager
import com.deividasstr.ui.features.main.backgroundwork.DownloadAllSweetsWorker
import java.util.UUID
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val backgroundWorkManager: BackgroundWorkManager,
    private val networkManager: NetworkManager
) : BaseViewModel() {

    fun tryDownloadSweets() {
        if (sharedPrefs.sweetsUpdatedDate == 0L) {
            scheduleDownload(backgroundWorkManager.downloadSweetsAndSaveDownloadDate())
        }
    }

    fun tryDownloadFacts() {
        if (sharedPrefs.factsUpdatedDate == 0L) {
            scheduleDownload(backgroundWorkManager.downloadFactsAndSaveDownloadDate())
        }
    }

    private fun scheduleDownload(workId: UUID) {
        val workManager = WorkManager.getInstance()
        val workStatus = workManager.getWorkInfoByIdLiveData(workId)
        _errorMessage.addSource(workStatus) {
            if (it.outputData.getBoolean(DownloadAllSweetsWorker.KEY_ERROR, false)) {
                setError(Error(R.string.error_network_server))
            }
        }

        if (!networkManager.networkAvailable) {
            setError(Error(R.string.error_network_unavailable))
        }
    }
}
