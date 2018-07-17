package com.deividasstr.ui.features.facts

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.SingleEvent
import com.deividasstr.ui.base.models.FactUi
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FactsViewModel
@Inject constructor(private val getRandomFactUseCase: GetRandomFactUseCase) : BaseViewModel() {

    private val fact = MutableLiveData<FactUi>()

    val factText = MediatorLiveData<String>().apply {
        addSource(fact) {
            this.value = it?.text
        }
    }

    init {
        getFact(0)
    }

    fun getNewFact() {
        fact.value?.let {
            getFact(it.id)
        }
    }

    private fun getFact(currentFactId: Long) {
        val disposable = getRandomFactUseCase.execute(currentFactId)
            .subscribeOn(Schedulers.io())
            .map { FactUi(it) }
            .subscribeBy(onSuccess = { fact.postValue(it) },
                onError = {
                    fact.postValue(null)
                    _errorMessage.postValue(SingleEvent(it as StringResException)) }
            )
        addDisposable(disposable)
    }
}