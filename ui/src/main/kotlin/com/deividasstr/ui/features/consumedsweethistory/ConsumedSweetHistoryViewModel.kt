package com.deividasstr.ui.features.consumedsweethistory

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.deividasstr.data.prefs.SharedPrefs
import com.deividasstr.domain.entities.DateTimeHandler
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConsumedSweetHistoryViewModel
@Inject constructor(
    private val getAllConsumedSweetsUseCase: GetAllConsumedSweetsUseCase,
    private val dateTimeHandler: DateTimeHandler,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    private val consumedSweets = MutableLiveData<List<ConsumedSweetUi>>()

    val sweetCells =
        MediatorLiveData<List<ConsumedSweetCell>>().apply {
        addSource(consumedSweets) { value = makeCells(it) }
    }

    init {
        getConsumedSweets()
    }

    private fun getConsumedSweets() {
        scope.launch {
            getAllConsumedSweetsUseCase {
                it.either(::handleError, ::handleSuccess) }
        }
    }

    private fun handleSuccess(sweets: List<ConsumedSweet>) {
        consumedSweets.postValue(sweets.toConsumedSweetUis())
    }

    private fun handleError(error: Error) {
        setError(error)
    }

    private fun makeCells(consumedSweets: List<ConsumedSweetUi>): List<ConsumedSweetCell> {
        return consumedSweets.map { consumedSweet ->
            ConsumedSweetCell(consumedSweet, dateTimeHandler, sharedPrefs.defaultMeasurementUnit)
        }
    }
}