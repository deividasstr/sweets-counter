package com.deividasstr.ui.features.consumedsweethistory

import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.usecases.GetSweetsByIdsUseCase
import com.deividasstr.ui.R
import com.deividasstr.ui.base.framework.BaseViewModel
import com.deividasstr.ui.base.framework.combineAndCompute
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.SweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import com.deividasstr.ui.base.models.toSweetUis
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryViewModel
@Inject constructor(
    private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase,
    private val getSweetsByIdsUseCase: GetSweetsByIdsUseCase
) : BaseViewModel() {

    private val consumedSweets = MutableLiveData<List<ConsumedSweetUi>>()
    private val sweets = MutableLiveData<List<SweetUi>>()

    val sweetsPair = sweets.combineAndCompute(consumedSweets) { sweets, consumedSweets ->
        Pair(consumedSweets, sweets)
    }

    init {
        getConsumedSweets()
    }

    private fun getConsumedSweets() {
        val disposable = getAllConsumedSweetsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map { it.toConsumedSweetUis() }
            .subscribeBy(onSuccess = { sweets ->
                val ids = sweets.map { it.sweetId.toLong() }.toLongArray()
                getSweets(ids)
                consumedSweets.postValue(sweets)
            },
                onError = {
                    val ex = it as StringResException
                    if (ex.messageStringRes == com.deividasstr.data.R.string.error_db_no_items) {
                        setError(StringResException(R.string.error_no_consumed_sweets))
                    } else {
                        setError(ex)
                    }
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