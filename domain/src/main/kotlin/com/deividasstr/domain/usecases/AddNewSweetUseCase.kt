package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.Sweet
import com.deividasstr.domain.framework.CompletableUseCaseWithParameter
import com.deividasstr.domain.repositories.SweetsRepo
import io.reactivex.Completable

class AddNewSweetUseCase(private val repo: SweetsRepo)
    : CompletableUseCaseWithParameter<Sweet> {

    override fun execute(parameter: Sweet): Completable {
        return repo.newSweet(parameter)
    }
}