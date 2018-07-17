package com.deividasstr.ui.utils.di

import com.deividasstr.domain.repositories.FactRepo
import com.deividasstr.domain.usecases.GetRandomFactUseCase
import com.deividasstr.ui.base.di.modules.UseCaseModule
import com.nhaarman.mockito_kotlin.mock

class TestUseCasesModule : UseCaseModule() {

    override fun provideGetRandomFactUseCase(repo: FactRepo): GetRandomFactUseCase {
        return mock()
    }
}