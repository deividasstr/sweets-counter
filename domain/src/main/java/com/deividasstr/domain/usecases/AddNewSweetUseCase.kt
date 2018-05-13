package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.framework.CompletableUseCaseWithParameter
import com.deividasstr.domain.repositories.NewSweetsRepo
import io.reactivex.Completable

class AddNewSweetUseCase(private val repo: NewSweetsRepo)
    : CompletableUseCaseWithParameter<Sweet> {

    override fun execute(parameter: Sweet): Completable {
        return repo.newSweet(parameter)
    }
}