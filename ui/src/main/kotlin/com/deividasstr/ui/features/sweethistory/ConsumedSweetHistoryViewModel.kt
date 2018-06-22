package com.deividasstr.ui.features.sweethistory

import com.deividasstr.domain.usecases.GetConsumedSweetsInPeriodUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class ConsumedSweetHistoryViewModel
@Inject constructor(private val getConsumedSweetsInPeriodUseCase: GetConsumedSweetsInPeriodUseCase) :
    BaseViewModel() {
}