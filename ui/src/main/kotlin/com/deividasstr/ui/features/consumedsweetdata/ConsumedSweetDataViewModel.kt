package com.deividasstr.ui.features.consumedsweetdata

import androidx.lifecycle.MutableLiveData
import com.deividasstr.domain.entities.enums.Periods
import com.deividasstr.domain.entities.models.ConsumedSweet
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.usecases.GetAllConsumedSweetsUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.ConsumedSweetUi
import com.deividasstr.ui.base.models.toConsumedSweetUis
import kotlinx.coroutines.launch
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
        scope.launch {
            getAllConsumedSweetsUseCase { it.either(::handleError, ::handleSuccess) }
        }
    }

    private fun handleSuccess(sweets: List<ConsumedSweet>) {
        consumedSweets.postValue(sweets.toConsumedSweetUis())
    }

    private fun handleError(error: Error) {
        setError(error)
    }
}