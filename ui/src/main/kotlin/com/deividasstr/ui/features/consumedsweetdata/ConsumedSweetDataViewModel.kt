package com.deividasstr.ui.features.consumedsweetdata

import com.deividasstr.domain.usecases.GetConsumedSweetsInPeriodUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class ConsumedSweetDataViewModel
@Inject constructor(private val getConsumedSweetsInPeriodUseCase: GetConsumedSweetsInPeriodUseCase) :
    BaseViewModel() {
}