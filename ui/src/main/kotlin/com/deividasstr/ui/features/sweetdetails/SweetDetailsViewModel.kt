package com.deividasstr.ui.features.sweetdetails

import com.deividasstr.domain.usecases.GetSweetByIdUseCase
import com.deividasstr.ui.base.framework.BaseViewModel
import javax.inject.Inject

class SweetDetailsViewModel
@Inject constructor(private val getSweetByIdUseCase: GetSweetByIdUseCase): BaseViewModel() {

}