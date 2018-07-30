package com.deividasstr.ui.base.framework

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.deividasstr.data.utils.StringResException
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    protected var _errorMessage = MediatorLiveData<SingleEvent<StringResException>>()

    val errorMessage: LiveData<SingleEvent<StringResException>>
        get() = _errorMessage

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    protected fun setError(error: StringResException) {
        _errorMessage.postValue(SingleEvent(error))
    }
}