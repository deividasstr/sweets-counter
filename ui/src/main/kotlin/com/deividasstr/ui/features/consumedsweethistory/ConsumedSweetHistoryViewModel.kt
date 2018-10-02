package com.deividasstr.ui.features.consumedsweethistory

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.data.utils.StringResException
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@DebugOpenClass
class ConsumedSweetHistoryViewModel
@Inject constructor(
    private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase,
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    private val consumedSweets = MutableLiveData<List<ConsumedSweetUi>>()

    val sweetCells =
        MediatorLiveData<List<ConsumedSweetCell>>().apply {
        addSource(consumedSweets) { postValue(makeCells(it)) }
    }

    init {
        getConsumedSweets()
    }

    private fun getConsumedSweets() {
        val disposable = getAllConsumedSweetsUseCase.execute()
            .subscribeOn(Schedulers.io())
            .map { it.toConsumedSweetUis() }
            .subscribeBy(onSuccess = { sweets ->
                consumedSweets.postValue(sweets)
            },
                onError = {
                    setError(it as StringResException)
                }
            )
        addDisposable(disposable)
    }

    private fun makeCells(consumedSweets: List<ConsumedSweetUi>): List<ConsumedSweetCell> {
        return consumedSweets.map { consumedSweet ->
            ConsumedSweetCell(consumedSweet, dateTimeHandler, sharedPrefs.defaultMeasurementUnit)
        }
    }
}