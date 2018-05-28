package com.deividasstr.domain.usecases

import com.deividasstr.domain.entities.ConsumedSweet
import com.deividasstr.domain.framework.CompletableUseCaseWithParameter
import com.deividasstr.domain.repositories.ConsumedSweetsRepo
import io.reactivex.Completable

class AddConsumedSweetUseCase(private val repo: ConsumedSweetsRepo)
    : CompletableUseCaseWithParameter<ConsumedSweet> {

    override fun execute(parameter: ConsumedSweet): Completable {
        return repo.addSweet(parameter)
    }
}