package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.enums.Periods
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.combineAndCompute
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import com.deividasstr.ui.base.models.toSweetUis
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ConsumedSweetDataViewModel
@Inject constructor(
    private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase,
    private val getSweetsByIdsUseCase: GetSweetsByIdsUseCase
) : BaseViewModel() {

    private var consumedSweets = MutableLiveData<List<ConsumedSweetUi>>()
    private var sweets = MutableLiveData<List<SweetUi>>()

    val sweetsPair = sweets.combineAndCompute(consumedSweets) { sweets,
        consumedSweets ->
        Pair(consumedSweets!!, sweets)
    }

    val currentPeriod = MutableLiveData<Periods>().apply { postValue(Periods.WEEK) }

    init {
        getConsumedSweets()
    }

    fun changePeriod(pos: Int) {
        val period = Periods.values()[pos + 1]
        if (currentPeriod.value != period) {
            currentPeriod.postValue(period)
        }
    }

    private fun getConsumedSweets() {
        val disposable = getAllConsumedSweetsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map { it.toConsumedSweetUis() }
            .subscribeBy(onSuccess = { it ->
                val ids = it.map { it.sweetId.toLong() }.toLongArray()
                consumedSweets.postValue(it)
                getSweets(ids)
            },
                onError = {
                    val ex = it as StringResException
                    setError(ex)
                }
            )
        addDisposable(disposable)
    }

    private fun getSweets(ids: LongArray) {
        val disposable = getSweetsByIdsUseCase.execute(ids)
            .subscribeOn(Schedulers.io())
            .map { it.toSweetUis() }
            .subscribeBy(onSuccess = {
                sweets.postValue(it)
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
    }
}