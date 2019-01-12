package com.deividasstr.ui.features.facts

import androidx.lifecycle.MutableLiveData
import com.deividasstr.domain.entities.models.Error
import com.deividasstr.domain.entities.models.Fact
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import com.deividasstr.ui.base.models.FactUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class FactsViewModel
@Inject constructor(private val getRandomFactUseCase: GetRandomFactUseCase) : BaseViewModel() {

    companion object {
        const val FIRST_FACT = 0L
    }

    val liveFact = MutableLiveData<FactUi>()

    init {
        getFact(FIRST_FACT)
    }

    fun getNewFact() {
        liveFact.value?.let { getFact(it.id) } ?: getFact(FIRST_FACT)
    }

    private fun getFact(currentFactId: Long) {
        scope.launch {
            getRandomFactUseCase(currentFactId) { it.either(::handleError, ::handleSuccess) }
        }
    }

    private fun handleSuccess(fact: Fact) {
        liveFact.postValue(FactUi(fact))
    }

    private fun handleError(error: Error) {
        setError(error)
    }
}