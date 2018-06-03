package com.deividasstr.ui.features.perioddetails

import com.deividasstr.domain.usecases.GetConsumedSweetsInPeriodUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class PeriodDetailsViewModel
@Inject constructor(private val getConsumedSweetsInPeriodUseCase: GetConsumedSweetsInPeriodUseCase) :
    BaseViewModel() {
}