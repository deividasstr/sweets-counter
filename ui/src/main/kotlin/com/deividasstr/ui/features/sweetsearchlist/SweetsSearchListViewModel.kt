package com.deividasstr.ui.features.sweetsearchlist

import com.deividasstr.domain.usecases.SearchSweetUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class SweetsSearchListViewModel
@Inject constructor(private val searchSweetUseCase: SearchSweetUseCase): BaseViewModel() {
}