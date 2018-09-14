package com.deividasstr.ui.features.addconsumedsweet

import com.deividasstr.domain.usecases.AddConsumedSweetUseCase
import com.deividasstr.domain.usecases.SearchSweetUseCase
import com.deividasstr.ui.base.framework.base.BaseViewModel
import javax.inject.Inject

class AddConsumedSweetViewModel
@Inject constructor(
    private val addConsumedSweetUseCase: AddConsumedSweetUseCase,
    private val searchSweetUseCase: SearchSweetUseCase
) : BaseViewModel()