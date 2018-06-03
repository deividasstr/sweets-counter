package com.deividasstr.ui.features.facts

import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class FactsViewModel
@Inject constructor(private val getRandomFactUseCase: GetRandomFactUseCase) : BaseViewModel() {

}