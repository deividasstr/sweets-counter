package com.deividasstr.ui.base.framework.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.ui.base.framework.SingleEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    private val job = Job()
    protected val scope = CoroutineScope(job + Dispatchers.IO)

    protected var _errorMessage = MediatorLiveData<SingleEvent<Error>>()

    val errorMessage: LiveData<SingleEvent<Error>>
        get() = _errorMessage


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    protected fun setError(error: Error) {
        _errorMessage.postValue(SingleEvent(error))
    }
}