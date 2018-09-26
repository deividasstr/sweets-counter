package com.deividasstr.ui.features.facts

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.FactUi
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FactsViewModel
@Inject constructor(private val getRandomFactUseCase: GetRandomFactUseCase) : BaseViewModel() {

    companion object {
        const val FIRST_FACT = 0L
    }

    val fact = MutableLiveData<FactUi>()

    init {
        getFact(FIRST_FACT)
    }

    fun getNewFact() {
        fact.value?.let { getFact(it.id) } ?: getFact(FIRST_FACT)
    }

    private fun getFact(currentFactId: Long) {
        val disposable = getRandomFactUseCase.execute(currentFactId)
            .subscribeOn(Schedulers.io())
            .map { FactUi(it) }
            .subscribeBy(onSuccess = {
                fact.postValue(it)
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
    }
}