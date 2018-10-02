package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ConsumedSweetDataViewModel
@Inject constructor(
    private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase
) : BaseViewModel() {

    val consumedSweets = MutableLiveData<List<ConsumedSweetUi>>()
    val currentPeriod = MutableLiveData<Periods>()

    init {
        getConsumedSweets()
    }

    fun changePeriod(pos: Int) {
        val period = Periods.values()[pos + 1]
        if (currentPeriod.value != period) currentPeriod.postValue(period)
    }

    private fun getConsumedSweets() {
        val disposable = getAllConsumedSweetsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map { it.toConsumedSweetUis() }
            .subscribeBy(onSuccess = { it ->
                consumedSweets.postValue(it)
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
    }
}