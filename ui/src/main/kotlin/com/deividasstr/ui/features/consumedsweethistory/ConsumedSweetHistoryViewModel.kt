package com.deividasstr.ui.features.consumedsweethistory

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.data.utils.DebugOpenClass
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import kotlinx.coroutines.launch
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
        addSource(consumedSweets) {
            println("new val $it")
            value = makeCells(it) }
    }

    init {
        getConsumedSweets()
    }

    private fun getConsumedSweets() {
        scope.launch {
            println("on launch")
            getAllConsumedSweetsUseCase {
                println("on usecase")
                it.either(::handleError, ::handleSuccess) }
        }
    }

    private fun handleSuccess(sweets: List<ConsumedSweet>) {
        println("handleSuccess $sweets")

        consumedSweets.postValue(sweets.toConsumedSweetUis())
    }

    private fun handleError(error: Error) {
        println("error $error")
        setError(error)
    }

    private fun makeCells(consumedSweets: List<ConsumedSweetUi>): List<ConsumedSweetCell> {
        return consumedSweets.map { consumedSweet ->
            ConsumedSweetCell(consumedSweet, dateTimeHandler, sharedPrefs.defaultMeasurementUnit)
        }
    }
}